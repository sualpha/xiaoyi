<script setup name="vip">
	import {
		request
	} from "@/request/index.js";
	import {
		ref
	} from 'vue';
	import {
		onMounted
	} from '@/hooks/onMounted';

	const customerQrCode = ref('');
	const redeemCode = ref('');

	const getCustomerQrCode = () => {
		return request({
				url: "/mini/config/customerQrCode"
			})
			.then((result) => {
				customerQrCode.value = result;
			});
	};

	const useRedeemCode = () => {
		const data = {
			redeemCode: redeemCode.value
		}

		return request({
				url: "/mini/member/useRedeemCode",
				method: "post",
				data
			})
			.then((result) => {
				uni.showToast({
					icon: 'success',
					title: result
				})
			}).catch((err) => {
				uni.showToast({
					icon: 'error',
					title: err
				})
			})
	};

	const onChange = (e) => {
		redeemCode.value = e.detail;
	};

	const onSubmit = () => {
		useRedeemCode();
	};

	onMounted(() => {
		getCustomerQrCode();
	});
</script>

<template>
	<view class="container">
		<view class="content">
			<van-field placeholder="请输入卡密兑换会员" custom-style=" width: 590rpx" border="{{ false }}" @change="onChange" />

			<van-button custom-style="
		width: 590rpx;
          marginTop: 36rpx;
          border: none;
          fontSize: 36rpx;" round type="primary" @click="onSubmit">
				确认兑换</van-button>

			<image class="pic" show-menu-by-longpress :src="customerQrCode" mode="widthFix" />
		</view>
	</view>
</template>

<style src="./style.scss" lang="scss" scoped />