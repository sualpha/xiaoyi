<script setup name="detail">
import { ref, reactive,computed } from 'vue';
import {
		onMounted
} from '@/hooks/onMounted';
import {
		request
} from "@/request/index.js";
import {
		DateModeEnum,
		BillTypeEnum,
		PageStatusEnum
	} from '@/enums';
	
	
import { onShareAppMessage } from '@/hooks/onShareAppMessage';

onShareAppMessage();
	
// 账单信息
const detail = reactive({
    id: '',
    type: '',
    categoryUrl: '',
    categoryName: '',
    transactionDate: '',
    price: '',
    description: ''
});

const formatprice = computed(() =>{
		if(detail.type===BillTypeEnum.EXPENSES){
			return "-" +detail.price;
		}
		return detail.price;
});
	
const onEditButtonClick = () => {

	uni.navigateTo({
		url: `/pages/record/index?id=${detail.id}`
	});

};

const onDeleteButtonClick = (id) => {
	uni.showModal({
		title: '提示',
		content: '删除后无法恢复，是否删除？',
		showCancel: true,
	}).then(({ confirm }) => {
		if (confirm) {
		request({url: "/mini/ledger/details/delete/"+detail.id,method: "post"})
			.then(() => {
				uni.showToast({ title: `已删除`, icon: 'none' });
				setTimeout(() => {
				    // 通知首页刷新
				    uni.$emit('billDeleted', {});
				    uni.navigateBack();
				}, 500);
			});
		}
	});
};

onMounted(({
    id
}) => {
   request({
   		url: "/mini/ledger/details/queryDetail/" + id,method: "post"
   	}).then(item =>{
   		detail.id = item.id;
   		detail.type = item.type.toString();
   		detail.transactionDate = item.transactionDate;
   		detail.categoryUrl = item.categoryUrl;
		detail.categoryName = item.categoryName;
   		detail.price = item.price.toString();
   		detail.description = item.description;
   	});
   }

);


</script>

<template>
   <view  class="content">
        <view class="tag">

            <view class="tag-icon"
                  :class="{
                      'expenses': detail.type === BillTypeEnum.EXPENSES,
                      'income': detail.type === BillTypeEnum.INCOME
                  }">

                <image :src="detail.categoryUrl" />

            </view>

            <text class="tag-name">{{ detail.categoryName }}</text>

        </view>

        <view class="amount">{{ formatprice }}</view>

        <view class="cell">

            <view class="label">记账日期</view>
            <view class="value">{{ detail.transactionDate }}</view>

        </view>

        <view class="cell">

            <view class="label">备注</view>
            <view class="value">{{ detail.description || '-' }}</view>

        </view>

        <view class="button-content">

            <view class="button button-delete"
                  hover-class="default-hover-class"
                  hover-stay-time="100"
                  @click="onDeleteButtonClick">

                <image src="../../static/svgs/icon_delete.svg" />
                <text class="delete">删除</text>

            </view>

            <view class="button"
                  hover-class="default-hover-class"
                  hover-stay-time="100"
                  @click="onEditButtonClick">

                <image src="../../static/svgs/icon_edit.svg" />
                <text class="edit">编辑</text>

            </view>

        </view>
	 </view>

</template>

<style src="./style.scss" lang="scss" scoped/>
