<script setup name="record">
	import {
		request
	} from "@/request/index.js";
	import { ref, reactive, computed } from 'vue';
	import {
		DateModeEnum,
		BillTypeEnum,
		PageStatusEnum
	} from '@/enums';
	import moment from 'moment';
	import {
		onMounted
	} from '@/hooks/onMounted';
	import { minDate } from '@/config';
import { result } from 'lodash';
	
	// 表单信息
	const formData = reactive({
	    id: '',
	    type: '',
	    transactionDate: '',
	    memberCategoryId: '',
	    price: '',
	    description: ''
	});
	// 备注相关
	const remarkInput = ref('');
	const showRemarkPopup = ref(false);
	
	// 键盘
	const numbers = ref(["1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "."]);
	const formatDate = computed(() => moment(formData.transactionDate).format('M月D日'));
	
	// 日历选择相关
	const showCalendar = ref(false);
	const calendarMinDate = moment(minDate).valueOf();
	const calendarMaxDate = moment().valueOf();
	
	
	let expenseTags = ref([]);
	let incomeTags = ref([]);
	
	const onSelectIncomeTags= () => {
		const data ={
			type:BillTypeEnum.INCOME
		}
		request({
			url: "/mini/member/queryMemberCategory",method: "post",data
		}).then(result=>{
			incomeTags=result;
		})
	};
	
	const onSelectExpenseTags= (id) => {
	const data ={
		type:BillTypeEnum.EXPENSES
	}
	request({
		url: "/mini/member/queryMemberCategory",method: "post",data
	}).then(result=>{
		expenseTags =result;
	
		if (!id) {
		    formData.id = '';
		    formData.type = BillTypeEnum.EXPENSES;
		    formData.transactionDate = moment().format('YYYY-MM-DD');
		    formData.memberCategoryId = expenseTags[0].id;
		    formData.price = '';
		    formData.description = '';
		    remarkInput.value = '';
			
		} else {
			request({
				url: "/mini/ledger/details/queryDetail/" + id,method: "post"
			}).then(item =>{
				formData.id = item.id;
				formData.type = item.type.toString();
				formData.transactionDate = item.transactionDate;
				formData.memberCategoryId = item.memberCategoryId;
				formData.price = item.price.toString();
				formData.description = item.description;
				remarkInput.value = item.description;
			});
		}
	})
	};
	
	const onNumberClick = (number) => {
	    wx.vibrateShort({
	        type: 'light'
	    });
	
	    const dotIndex = formData.price.indexOf('.');
	
	    if (number === '.') {
	
	        // 如果第一个是小数点
	        if (formData.price === '') {
	            formData.price = '0.';
	            return;
	        }
	
	        // 只能输入一个小数点
	        if (dotIndex > -1) {
	            return;
	        }
	
	    }
	
	    // 小数点后只能有两位数字
	    if (dotIndex > -1 && formData.price.substring(dotIndex, formData.price.length).length > 2) {
	        return;
	    }
	
	    // 金额不能大于1000000
	    if (formData.price !== '' && Number(formData.price + number) > 1000000) {
	        uni.showToast({ title: '输入金额不能大于1,000,000', icon: 'none' });
	        return;
	    }
	
	    formData.price += number;
	};
	
	const onBackSpaceClick = () => {
	
	    wx.vibrateShort({
	        type: 'light'
	    });
	
	    if (formData.price === '') {
	        return;
	    }
	
	    formData.price = formData.price.substring(0, formData.price.length - 1);
	
	};
	
	const onRemarkPopupOpen = () => {
	
	    showRemarkPopup.value = true;
	    remarkInput.value = formData.description;
	
	};
	
	const onRemarkPopupClose = () => {
	
	    showRemarkPopup.value = false;

	};
	
	const onSaveRemarkButtonClick = () => {
	
	    if (remarkInput.value.length === 0) {
	        return;
	    }
	
	    showRemarkPopup.value = false;
	    formData.description = remarkInput.value;
	
	};
	
	const onTagItemClick = (tagId) => {
	
	    formData.memberCategoryId = tagId;
	
	};
	
	const onTabItemClick = (type) => {
	    formData.type = type;
	    formData.memberCategoryId = type === BillTypeEnum.EXPENSES ? expenseTags[0].id : incomeTags[0].id;
		uni.setNavigationBarColor({
			frontColor: '#ffffff',
			backgroundColor: type === BillTypeEnum.EXPENSES ? '#3eb575' : '#f0b73a'
		})
	};
	
	const onConfirmButtonClick = () => {
	
	    wx.vibrateShort({
	        type: 'light'
	    });
	
	    if (!formData.price) {
	        uni.showToast({ title: '请输入具体金额', icon: 'none' });
	        return;
	    }
	
	    if (Number(formData.price) < 0.01) {
	        uni.showToast({ title: '所输金额不得小于0.01', icon: 'none' });
	        return;
	    }
	
	    uni.showLoading({ title: '记录中', mask: true });
		 const data= formData;
		 if (!formData.id) {
			 request({
			 	url: "/mini/ledger/details/create",method: "post",data
			 }).then(() => {
	                uni.showToast({ title: '已记一笔', icon: 'success', duration: 1000 });
	                setTimeout(() => {
	                    uni.navigateBack();
	                    // 通知首页刷新
	                    uni.$emit('billCreated', {});
	                }, 500);
	            })
		 }else {
			 request({
			 	url: "/mini/ledger/details/update",method: "post",data
			 }).then(() => {
			        uni.showToast({ title: '编辑成功', icon: 'success', duration: 1000 });
			      setTimeout(() => {
			          if (getCurrentPages().length === 3) {
			              uni.navigateBack({
			                  delta: 2
			              });
			          } else {
			              uni.navigateBack();
			          }
			          // 通知首页刷新
			          uni.$emit('billUpdated', {});
			      }, 500);
			    })
	        }
	};
	
	const onCalendarOpen = () => {
	
	    showCalendar.value = true;
	
	};
	
	const onCalendarClose = () => {
	
	    showCalendar.value = false;
	
	};
	
	const onCalendarSelect = ({ detail }) => {
	
	    formData.transactionDate = moment(detail.getTime()).format('YYYY-MM-DD');
	
	    onCalendarClose();
	};
	
	onMounted(({
	    id
	}) => {
	    uni.setNavigationBarTitle({
	        title: !id ? '记一笔' : '编辑'
	    });
		onSelectIncomeTags();
		onSelectExpenseTags(id);
	});
</script>

<template>
	
	<view class="content">
	
	    <view class="action-content">
	
	        <view class="tab">
	
	            <view class="tab-item"
	                  :class="{ 'expenses': formData.type === BillTypeEnum.EXPENSES }"
	                  :hover-class="formData.type === BillTypeEnum.EXPENSES ? '' : 'gray-hover-class'"
	                  hover-stay-time="100"
	                  @click="onTabItemClick(BillTypeEnum.EXPENSES)">支出
	            </view>
	
	            <view class="tab-item"
	                  :class="{ 'income': formData.type === BillTypeEnum.INCOME }"
	                  :hover-class="formData.type === BillTypeEnum.INCOME ? '' : 'gray-hover-class'"
	                  hover-stay-time="100"
	                  @click="onTabItemClick(BillTypeEnum.INCOME)">收入
	            </view>
	
	        </view>
	
	        <view v-if="formData.transactionDate"
	              class="date"
	              hover-class="gray-hover-class"
	              hover-stay-time="100"
	              @click="onCalendarOpen">
	
	            <text>{{ formatDate }}</text>
	            <image src="../../static/svgs/icon_down.svg" />
	
	        </view>
	
	    </view>
	
	    <view class="amount-content">
	
	        <image src="../../static/svgs/icon_yuan.svg" />
	        <text class="amount">{{ formData.price }}</text>
	        <span class="cursor"></span>
	
	    </view>
	
	    <view class="tag-content">
	
	        <view v-show="formData.type === BillTypeEnum.EXPENSES">
	
	            <view class="tag-wrapper">
	
	                <view v-for="category in expenseTags"
	                      :key="category.id"
	                      class="tag"
	                      :class="{ 'expenses': formData.memberCategoryId === category.id }"
	                      @click="onTagItemClick(category.id)">
	
	                    <view class="tag-icon">
	
	                        <image v-show="formData.memberCategoryId === category.id"
	                               :src="category.categoryUrl" />
	                        <image v-show="formData.memberCategoryId !== category.id"
	                               :src="category.categoryGreyUrl" />
	
	                    </view>
	
	                    <view class="tag-name">{{ category.categoryName }}</view>
	
	                </view>
	
	            </view>
	
	        </view>
	
	        <view v-show="formData.type === BillTypeEnum.INCOME">
	
	            <view class="tag-wrapper">
	
	                <view v-for="category in incomeTags"
	                      :key="category.id"
	                      class="tag"
	                      :class="{ 'income': formData.memberCategoryId === category.id }"
	                      @click="onTagItemClick(category.id)">
	
	                    <view class="tag-icon">
	
	                        <image v-show="formData.memberCategoryId === category.id"
	                               :src="category.categoryUrl" />
	                        <image v-show="formData.memberCategoryId !== category.id"
	                               :src="category.categoryGreyUrl" />
	
	                    </view>
	
	                    <view class="tag-name">{{ category.categoryName }}</view>
	
	                </view>
	
	            </view>
	
	        </view>
	
	    </view>
	
	    <view class="remark-content">
	
	        <view v-show="formData.description.length === 0"
	              class="remark-action"
	              hover-class="default-hover-class"
	              hover-stay-time="100"
	              @click="onRemarkPopupOpen">添加备注
	        </view>
	
	        <view class="remark">{{ formData.description }}</view>
	
	        <view v-show="formData.description.length !== 0"
	              class="remark-action"
	              hover-class="default-hover-class"
	              hover-stay-time="100"
	              @click="onRemarkPopupOpen">修改
	        </view>
	
	    </view>
	
	    <view class="keyboard-content">
	
	        <view class="keyboard-wrapper" style="width:75%">
	
	            <view v-for="(number, index) in numbers"
	                  :key="index"
	                  class="keyboard"
	                  :class="{ 'keyboard-0': number === '0' }">
	
	                <view class="keyboard-item"
	                      hover-class="gray-hover-class"
	                      hover-stay-time="100"
	                      @click="onNumberClick(number)">
	
	                    {{ number }}
	
	                </view>
	
	            </view>
	
	        </view>
	
	        <view class="keyboard-wrapper" style="width:25%">
	
	            <view class="keyboard-back">
	
	                <view class="keyboard-item"
	                      hover-class="gray-hover-class"
	                      hover-stay-time="100"
	                      @click="onBackSpaceClick">
	
	                    <image src="../../static/svgs/icon_backspace.svg" />
	
	                </view>
	
	            </view>
	
	            <view class="keyboard-confirm">
	
	                <view class="keyboard-item"
	                      :class="{
	                          'expenses': formData.type === BillTypeEnum.EXPENSES,
	                          'income': formData.type === BillTypeEnum.INCOME
	                      }"
	                      hover-class="default-hover-class"
	                      hover-stay-time="100"
	                      @click="onConfirmButtonClick">确定
	                </view>
	
	            </view>
	
	        </view>
	
	    </view>
	
	    <van-calendar title="请选择时间"
	                  :show="showCalendar"
	                  :showConfirm="false"
	                  :min-date="calendarMinDate"
	                  :max-date="calendarMaxDate"
	                  :color="formData.type === BillTypeEnum.EXPENSES ? '#3eb575' : '#f0b73a'"
	                  @close="onCalendarClose"
	                  @select="onCalendarSelect" />
	
	    <van-popup :show="showRemarkPopup"
	               round
	               position="bottom"
	               custom-style="height: 600rpx"
	               closeable
	               @close="onRemarkPopupClose">
	
	        <view class="popup">
	
	            <view class="popup-title">添加备注</view>
	
	            <input class="popup-input"
	                   v-model="remarkInput"
	                   cursor-spacing="200"
	                   maxlength="30"
	                   placeholder="请输入备注内容"
	                   :adjust-position="true" />
	
	            <view class="popup-length">{{ remarkInput.length }}/30</view>
	
	            <view class="popup-button"
	                  :class="{
	                      'expenses': formData.type === BillTypeEnum.EXPENSES && remarkInput.length > 0,
	                      'income': formData.type === BillTypeEnum.INCOME && remarkInput.length > 0
	                  }"
	                  :hover-class="remarkInput.length > 0 ? 'default-hover-class' : ''"
	                  hover-stay-time="100"
	                  @click="onSaveRemarkButtonClick">确 定
	            </view>
	
	        </view>
	
	    </van-popup>
	</view>

</template>

<style>
page {
    height: 100%;
}
</style>


<style src="./style.scss" lang="scss" scoped/>
