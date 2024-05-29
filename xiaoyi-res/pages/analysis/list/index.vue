<script setup name="analysis-list">
	import {
		ref,
		computed
	} from 'vue';
	import {
		onPullDownRefresh,
		onReachBottom,
		onPageScroll
	} from "@dcloudio/uni-app";
	import {
		onMounted
	} from '@/hooks/onMounted';
	import {
		useState
	} from '@/hooks/useState';
	import {
		defaultPageSize
	} from '@/config';
	import {
		ListTypeEnum,
		BillTypeEnum,
		PageStatusEnum
	} from '@/enums';
	import moment from 'moment';
	import BackToTop from '@/components/back-to-top';
	import {
		request
	} from "@/request/index.js";
	import _ from 'lodash';

	// 条件
	const startTime = ref('');
	const endTime = ref('');
	const memberCategoryId = ref('');
	const billType = ref('');
	const isYear = ref(false);
	// 加载
	const loading = ref(true);
	// 类型(按金额分类/按时间分类)
	const activeListType = ref(ListTypeEnum.AMOUNT);
	// 列表相关
	const pageNumber = ref(1);
	const pageSize = ref(defaultPageSize);
	const pageStatus = ref('');
	const list = ref([]);
	// 统计
	const totalAmount = ref(0);
	const totalCount = ref(0);
	// 显示返回顶部
	const showBackToTop = ref(false);

	const formatBillTime = computed(() => (billTime) => isYear.value ? moment(billTime).format('YYYY年M月D日') : moment(
		billTime).format('M月D日'));

	const getBillList = (data) => {
		return request({
				url: "/mini/ledger/details/queryPageList",
				method: "post",
				data
			})
			.then((result) => {
				return result;
			});
	};
	
	const getBillStatisticsGroupByTag = (data) => {
		return request({
				url: "/mini/statistics/statisticsByCategory",
				method: "post",
				data
			})
			.then((result) => {
				return result;
			});
	};

	onMounted((opts) => {
		
		if (opts.billType === BillTypeEnum.INCOME) {
		
			uni.setNavigationBarColor({
				frontColor: '#ffffff',
				backgroundColor: '#f0b73a'
			});
					
			uni.setBackgroundColor({
				backgroundColorTop: '#f0b73a'
			});
		
		} else {
		
			uni.setNavigationBarColor({
			    frontColor: '#ffffff',
			    backgroundColor: '#3eb575'
			});
					
			uni.setBackgroundColor({
			    backgroundColorTop: '#3eb575'
			});
		
		};
		
		uni.setNavigationBarTitle({
			title: opts.title,
		});

		startTime.value = opts.startTime;
		endTime.value = opts.endTime;
		memberCategoryId.value = opts.tagId;
		billType.value = opts.billType;
		isYear.value = opts.title.includes('年');

		onQuery();
		loading.value = false;
		uni.hideLoading();
		
	});
	
	const onTabItemClick = (listType) => {
	
	    activeListType.value = listType;
	
	    onClear();
	
	};

	const onQuery = () => {

		const data = {
			type: billType.value,
			memberCategoryId: memberCategoryId.value,
			startTime: startTime.value,
			endTime: endTime.value,
		/* 	orderBy,
			asc, */
		};
		
		Promise.all([
			getBillStatisticsGroupByTag(data),
			getBillList(_.assign(data, {
				pageNumber: pageNumber.value,
				pageSize: pageSize.value,
				orderBy: activeListType.value === ListTypeEnum.AMOUNT?'price':'transactionDate'
			}))
		]).then(([
			groupByTagData,
			listData
		]) => {
			// 顶部总计
			totalAmount.value = groupByTagData.totalAmt;
			totalCount.value = groupByTagData.totalCount;
			if (listData.list.length < pageSize.value) {
				pageStatus.value = PageStatusEnum.NOMORE;
			} else {
				pageStatus.value = PageStatusEnum.MORE;
			}
			if (listData.list.length === 0) {
				pageNumber.value--;
			} else {
				list.value = _.concat(
					list.value,
					listData.list
				);
			}
		});
	};

	const onClear = () => {

		uni.showLoading({
			title: '加载中',
			mask: true
		});
		
		pageStatus.value = '';
		pageSize.value = defaultPageSize;
		pageNumber.value = 1;
		list.value = [];

		onQuery();
		uni.hideLoading();
		uni.stopPullDownRefresh();

	};
</script>

<template>
	<view v-show="!loading" class="content">

		<view class="action-content">

			<view class="tab">

				<view class="tab-item" :class="{
                          'expenses': activeListType === ListTypeEnum.AMOUNT && billType === BillTypeEnum.EXPENSES,
                          'income': activeListType === ListTypeEnum.AMOUNT && billType === BillTypeEnum.INCOME
                      }" hover-stay-time="100" @click="onTabItemClick(ListTypeEnum.AMOUNT)">按金额
				</view>

				<view class="tab-item" :class="{
                          'expenses': activeListType === ListTypeEnum.TIME && billType === BillTypeEnum.EXPENSES,
                          'income': activeListType === ListTypeEnum.TIME && billType === BillTypeEnum.INCOME
                      }" hover-stay-time="100" @click="onTabItemClick(ListTypeEnum.TIME)">按时间
				</view>

			</view>

			<view class="total">

				{{ billType === BillTypeEnum.EXPENSES ? '支出' : '收入' }}¥{{ totalAmount }}

			</view>

		</view>

		<view class="list">

			<view v-for="(bill) in list" :key="bill.id" class="list-item" hover-class="gray-hover-class"
				hover-stay-time="100">

				<view class="icon" :class="{
                          'expenses': bill.type == BillTypeEnum.EXPENSES,
                          'income': bill.type == BillTypeEnum.INCOME
                      }">

					<image :src="bill.categoryUrl" />

				</view>

				<view class="item-content">

					<view class="tag-name">{{ bill.categoryName }}</view>
					<view class="remark">{{ bill.description }}</view>

				</view>

				<view class="info">

					<view v-if="bill.type == BillTypeEnum.EXPENSES" class="amount">
						- {{ bill.price }}
					</view>
					<view v-if="bill.type == BillTypeEnum.INCOME" class="amount">
						+ {{bill.price}}
					</view>

					<view class="bill-time">{{ formatBillTime(bill.transactionDate) }}</view>

				</view>

			</view>

		</view>

		<view class="loading-content">

			<van-loading v-show="pageStatus === PageStatusEnum.LOADING" size="30rpx"
				type="spinner">正在加载...</van-loading>
			<van-loading v-show="pageStatus === PageStatusEnum.NOMORE" size="30px"
				type="">没有更多数据了，快去记一笔吧^-^</van-loading>

		</view>

		<back-to-top :visible="showBackToTop" :bill-type="billType" />

		<!-- 用于解决ios的bug -->
		<view style="height: 1px" />

	</view>
</template>

<style src="./style.scss" lang="scss" scoped />