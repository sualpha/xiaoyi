<script setup name="home">
	import BackToTop from '@/components/back-to-top';
	import DatePicker from '@/components/date-picker';
	import {
		useState
	} from '@/hooks/useState';
	import {
		request
	} from "@/request/index.js";
	import {
		ref,
		computed
	} from 'vue';
	import moment from 'moment';
	import {
		DateModeEnum,
		BillTypeEnum,
		PageStatusEnum
	} from '@/enums';
	import {
		defaultPageSize
	} from '@/config';
	import {
		onMounted
	} from '@/hooks/onMounted';
	import {
		onPullDownRefresh,
		onReachBottom,
		onPageScroll
	} from "@dcloudio/uni-app";

	import {
		obtainIsVip
	} from '@/hooks/obtainIsVip';
	import { onShareAppMessage } from '@/hooks/onShareAppMessage';
	import Dialog from '@/wxcomponents/vant/dialog/dialog';

	onShareAppMessage();
	const {
		host,
		registerTime
	} = useState();
	
	const isNewMember =  computed(() => {
		const hoursDiff = moment().diff(moment(registerTime.value), 'hours');
		return hoursDiff <= 1;
	});

	const content = ref('');
	// 金额相关
	const totalExpenses = ref(0);
	const totalIncome = ref(0);
	const formatTotalAmount = ref(0);

	// 列表相关
	const pageNumber = ref(1);
	const pageSize = ref(defaultPageSize);
	const pageStatus = ref(PageStatusEnum.MORE);
	const list = ref([]);

	//录音相关
	const isLuyin = ref(false);
	let timer = -1;
	const count = ref(0);
	const startPoint = ref('');
	const isVip = ref(false);

	const recorderManager = uni.getRecorderManager();


	//日期
	const activeDate = ref(moment().format('YYYY-MM'));
	const activeDateMode = ref(DateModeEnum.MONTH);

	const showBackToTop = ref(false);
	// 日期选择器相关
	const showDatePicker = ref(false);



	const formatDate = computed(() => {
		if (activeDateMode.value === DateModeEnum.WEEK) {

		} else if (activeDateMode.value === DateModeEnum.MONTH) {
			return moment(activeDate.value).format('YYYY年M月');
		} else if (activeDateMode.value === DateModeEnum.YEAR) {
			return `${activeDate.value}年`;
		}
	});

	const getTimeRange = () => {
		let unit = 'month';
		if (activeDateMode.value === DateModeEnum.WEEK) {
			unit = 'week';
		} else if (activeDateMode.value === DateModeEnum.MONTH) {
			unit = 'month';
		} else if (activeDateMode.value === DateModeEnum.YEAR) {
			unit = 'year';
		}
		return {
			startTime: moment(activeDate.value).startOf(unit).format('YYYY-MM-DD'),
			endTime: moment(activeDate.value).add(1, unit).startOf(unit).format('YYYY-MM-DD')
		};

	};

	const onClear = () => {

		pageStatus.value = '';
		pageSize.value = defaultPageSize;
		pageNumber.value = 1;
		list.value = [];

		uni.showLoading({
			title: '加载中',
			mask: true
		});

		onQuery();
		uni.hideLoading();
		uni.stopPullDownRefresh();
		uni.pageScrollTo({
			scrollTop: 0,
			duration: 100
		});
		onQueryStatistics();

	};

	onPullDownRefresh(() => {

		onClear();

	});

	onReachBottom(() => {

		pageNumber.value++;

		onQuery();

	});

	onPageScroll(({
		scrollTop
	}) => {

		showBackToTop.value = scrollTop >= (uni.getSystemInfoSync().windowHeight / 4);

	});
	
	const onMemberInvite = (opts) => {
		if(opts.memberId){
			const data = {
				memberInitiateId: opts.memberId
			}
			request({
					url: "/mini/member/memberInvite",
					method: "post",
					data
			});
		}
	};

	onMounted((opts) => {
		onMemberInvite(opts);
		
		if(isNewMember.value){
			Dialog.alert({
				title: '感谢您选择使用小易记账助手',
				message: '本小程序旨在帮助用户更高效地进行记账，提供了独特的一句话语音记账功能和iOS系统Siri快捷记账功能，并计划未来推出更多基于语音的统计等功能。\n除语音相关服务外，本小程序的所有功能均为免费提供，感谢您的支持。对于新用户，我们提供了7天的免费语音服务试用期，希望能为您的记账体验提供帮助。\n我们承诺用户的数据绝对安全，如果您需要提取数据，请随时联系我们的客服。再次感谢您的选择和信任，如果您有任何疑问或需要帮助，请随时联系我们的客服团队。',
			});	
		}
		
		onQuery();
		onQueryStatistics();
		// 监听: 新建账单后
		uni.$on('billCreated', () => afterOperation());
		// 监听: 更新账单后
		uni.$on('billUpdated', () => afterOperation());
		// 监听: 删除账单后
		uni.$on('billDeleted', () => afterOperation());
		
		obtainIsVip().then(result=>{
			isVip.value = result.isVip;
		})

		recorderManager.onError((res) => {
			uni.authorize({
				scope: 'scope.record',
				success() {
					console.log('录音授权成功')
				}
			})
		})
	});

	// 新增、编辑、删除之后的操作
	const afterOperation = () => {
		onClear();
	};

	const onQuery = () => {
		const {
			startTime,
			endTime
		} = getTimeRange();

		const data = {
			pageNumber: pageNumber.value,
			pageSize: pageSize.value,
			startTime,
			endTime,
			content: content.value
		}

		request({
				url: "/mini/ledger/details/queryList",
				method: "post",
				data
			})
			.then((result) => {
				if (result.list.length < pageSize.value) {
					pageStatus.value = PageStatusEnum.NOMORE;
				} else {
					pageStatus.value = PageStatusEnum.MORE;
				}
				if (result.list.length === 0) {
					pageNumber.value--;
				} else {
					list.value = [...list.value, ...result.list];
				}
			});
	};



	const onQueryStatistics = () => {
		const data = getTimeRange();
		request({
				url: "/mini/ledger/details/queryMemberMonthLedgerSum",
				method: "post",
				data
			})
			.then((data) => {
				totalExpenses.value = data.expenditure;
				totalIncome.value = data.income;
				formatTotalAmount.value = data.sum;
			});
	};

	const onEditButtonClick = (id) => {

		uni.navigateTo({
			url: `/pages/record/index?id=${id}`
		});

	};

	const onRecordButtonClick = () => {

		uni.navigateTo({
			url: '/pages/record/index'
		});
	};

	const onBillItemClick = (id) => {

		uni.navigateTo({
			url: `/pages/detail/index?id=${id}`
		});
	};
	const onAnalysisClick = () => {
		uni.navigateTo({
			url: '/pages/analysis/index'
		});
	};
	const onVoiceClick = () => {
		uni.navigateTo({
			url: '/pages/quick-voice/index'
		});
	};

	const onSearch = (e) => {
		content.value = e.detail;
		afterOperation();
	};

	const onSearchCancel = (e) => {
		content.value = '';
		afterOperation();
	};

	const onDeleteButtonClick = (id) => {
		uni.showModal({
			title: '提示',
			content: '删除后无法恢复，是否删除？',
			showCancel: true,
		}).then(({
			confirm
		}) => {
			if (confirm) {
				request({
						url: "/mini/ledger/details/delete/" + id,
						method: "post"
					})
					.then(() => {
						uni.showToast({
							title: `已删除`,
							icon: 'none'
						});
						afterOperation();
					});
			}
		});
	};

	const onDatePickerOpen = () => {

		showDatePicker.value = true;

	};

	const onDatePickerClose = () => {

		showDatePicker.value = false;

		setTimeout(() => activeDateMode, 300);

	};

	const onDateModeChange = (mode) => {
		activeDateMode.value = mode;
	};

	const onDateSelect = (date) => {
		activeDate.value = date;

		onDatePickerClose();

		onClear();
	};

	// 开始录音
	const startRecorderManager = (e) => {
		uni.showLoading({
			title: '录音中上滑取消',
			icon: 'none'
		})
		wx.vibrateLong({
			complete: function() {
				isLuyin.value = true;
			}
		})
		let option = {
			duration: 60000, //指定录音的时长，单位 ms
			sampleRate: 8000, //采样率
			numberOfChannels: 1, //录音通道数
			encodeBitRate: 48000, //编码码率
			format: 'mp3', //音频格式
		}
		recorderManager.start(option)
		startPoint.value = e.touches[0]
		timer = setInterval(() => {
			count.value += 1;
			if (count.value >= 60) {
				clearInterval(timer);
				this.stopRecorderManager()
			}
		}, 1000);
	}
	// 松开按钮-结束录音
	const stopRecorderManager = (e) => {
		uni.hideLoading();
		clearInterval(timer);
		recorderManager.stop() //结束录音
		//对停止录音进行监控
		recorderManager.onStop((res) => {
			if (!isLuyin.value) {
				return
			}
			//对录音时长进行判断，少于2s的不进行发送，并做出提示
			if (res.duration < 1000) {
				if (res.duration > 15) {
					uni.showToast({
						title: '录音时间太短，请长按录音',
						icon: 'none',
						duration: 1000
					})
				}
			} else {
				const token = uni.getStorageSync('token');
				uni.showLoading({
					title: '语音识别中',
					icon: 'none'
				})
				uni.uploadFile({
					url: host.value + '/mini/ledger/details/voice',
					filePath: res.tempFilePath,
					header: {
						"content-type": "application/json; charset=utf-8",
						"XiaoYiToken": token || ""
					},
					name: 'file',
					success: res1 => {
						const result = JSON.parse(res1.data);
						setTimeout(() => {
								uni.hideLoading(),
								onClear();
						}, 3000)
						uni.showToast({
							title: result.data,
							icon: 'none',
							duration: 3000
						})
					},
					fail: error => {
						uni.hideLoading();
						uni.showToast({
							title: "语音记账异常，请稍后再试",
							icon: 'none'
						});
					}
				});
			}
		})
	}
	// 滑动取消录音
	const cancelRecorderManager = (e) => {
		//计算距离，当滑动的垂直距离大于25时，则取消发送语音
		if (startPoint.value.clientY - e.touches[0].clientY > 25) {
			console.log("取消录音")
			//取消滑动 - 不发送消息
			isLuyin.value = false;
		}
	}
	
</script>

<template>
	<view class="content">
		<view class="header">

			<view class="header-item" hover-class="default-hover-class" hover-stay-time="100" @click="onDatePickerOpen">

				<view class="header-item-text1">{{ formatDate }}</view>
				<image class="header-item-img" src='../../static/svgs/icon_down_white.svg' />

			</view>

			<!-- <view class="header-item" hover-class="default-hover-class" hover-stay-time="100" @click="onTagPickerOpen">

				<image class="header-item-img" src='../../static/svgs/icon_more.svg' />
				<view class="header-item-text2">全部类型</view>

			</view> -->

		</view>

		<view class="blank-content" />

		<view class="info-content">

			<view class="info-wrapper">
				<view class="statistics">
					<text class="label" v-if="activeDateMode===DateModeEnum.MONTH">月结余</text>
					<text class="label" v-if="activeDateMode===DateModeEnum.YEAR">年结余</text>
					<text class="value">{{formatTotalAmount}}</text>
				</view>
				<view class="statistics">
					<text class="label" v-if="activeDateMode===DateModeEnum.MONTH">月支出</text>
					<text class="label" v-if="activeDateMode===DateModeEnum.YEAR">年支出</text>
					<text class="value">{{ totalExpenses }}</text>
				</view>
				<view class="statistics">
					<text class="label" v-if="activeDateMode===DateModeEnum.MONTH">月收入</text>
					<text class="label" v-if="activeDateMode===DateModeEnum.YEAR">年收入</text>
					<text class="value">{{ totalIncome }}</text>
				</view>

			</view>
			<view class="tag-wrapper">

				<view class="tag" :class="{ 'expenses': true }" @click="onAnalysisClick">

					<view class="tag-icon">
						<image :src="'/static/tab/analysis.png'" />
					</view>

					<view class="tag-name">统计</view>
				</view>
				<view class="tag" :class="{ 'expenses': true }" @click="onVoiceClick" v-if="isVip">

					<view class="tag-icon">
						<image :src="'/static/tab/voice.png'" />
					</view>

					<view class="tag-name">快捷语音</view>
				</view>
			</view>

		</view>

		<view>
			<van-search shape="round" placeholder="输入金额或备注" @search="onSearch" @cancel="onSearchCancel" />
		</view>
		<view class="list-content">

			<view class="day-bill" v-for="item in list">

				<view class="statistics">

					<view class="time-content">

						<text>{{item.date }}</text>
						<text>{{item.week}}</text>

					</view>

					<view class="amount-content">

						<view class="label">支出</view>
						<view class="value">{{ item.totalExpenses}}</view>
						<view class="label">收入</view>
						<view class="value">{{ item.totalIncome }}</view>

					</view>

				</view>

				<view class="list-item-wrapper" v-for="bill in item.dayResult">

					<van-swipe-cell right-width="300">

						<view class="list-item" hover-class="gray-hover-class" hover-stay-time="100"
							@click="onBillItemClick(bill.id)">

							<view class="icon" :class="{
							          'expenses': bill.type === BillTypeEnum.EXPENSES,
							          'income': bill.type === BillTypeEnum.INCOME
							      }">

								<image :src="bill.categoryUrl" />

							</view>

							<view class="info">

								<view class="tag">{{ bill.categoryName }}</view>
								<view v-if="bill.description" class="remark">{{ bill.description }}</view>

							</view>

							<view class="amount">

								<text v-if="bill.type === BillTypeEnum.EXPENSES" class="expenses">
									- {{ bill.price }}
								</text>
								<text v-if="bill.type === BillTypeEnum.INCOME" class="income">
									+ {{ bill.price }}
								</text>

							</view>

						</view>

						<view class="swipe-content" slot="right">

							<view class="swipe-item swipe-edit" @click="onEditButtonClick(bill.id)">
								编辑
							</view>

							<view class="swipe-item swipe-delete" @click="onDeleteButtonClick(bill.id)">
								删除
							</view>

						</view>

					</van-swipe-cell>

				</view>

			</view>

			<view v-if="pageStatus === PageStatusEnum.NOMORE && list.length === 0" class="no-data">

				<image src="../../static/svgs/pic_no_more.svg" />
				<text>暂无账单，快去记一笔吧^-^</text>

			</view>

			<view v-if="list.length !== 0" class="loading-content">

				<van-loading v-show="pageStatus === PageStatusEnum.LOADING" size="30rpx" type="spinner">
					正在加载...
				</van-loading>

				<van-loading v-show="pageStatus === PageStatusEnum.NOMORE" size="30px" type="">
					没有更多数据了，快去记一笔吧^-^
				</van-loading>

			</view>

		</view>

		<back-to-top :visible="showBackToTop" />

		<view class="record-button" hover-class="gray-hover-class" hover-stay-time="100"
			@tap.native.stop="onRecordButtonClick" v-if="!isVip">

			<image src="../../static/svgs/icon_record.svg" />
			<text>记一笔</text>

		</view>

		<view class="record-button" hover-class="gray-hover-class" hover-stay-time="100" @click="onRecordButtonClick"
			@longpress="startRecorderManager" @touchend="stopRecorderManager" @touchmove="cancelRecorderManager" v-if="isVip">
			<image src="../../static/svgs/icon_record.svg" />
			<text>记一笔(长按语音记)</text>
		</view>

		<date-picker :visible="showDatePicker" :active-mode="activeDateMode" :active-date="activeDate"
			@change="onDateModeChange" @select="onDateSelect" @close="onDatePickerClose" />

		<tag-picker :visible="showTagPicker" :active-tag-id="activeTagId" @select="onTagSelect"
			@close="onTagPickerClose" />

		<!-- 用于解决ios的bug -->
		<view style="height: 1px" />
		<van-dialog id="van-dialog" messageAlign="left" />

	</view>
</template>

<style src="./style.scss" lang="scss" scoped />