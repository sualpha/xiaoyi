<script setup name="member-invite">
	import {
		ref
	} from 'vue';
	import {
		onShareAppMessage
	} from "@dcloudio/uni-app";
	import {
		onMounted
	} from '@/hooks/onMounted';
	import {
		useState
	} from '@/hooks/useState';
	import {
		request
	} from "@/request/index.js";

	// 全局信息
	const {
		memberId
	} = useState();
	
	const inviteCount = ref(0);
	const inviteDay = ref(0);

	// 分享的信息
	onShareAppMessage((res) => {
		return {
			title: '欢迎来到小易记账助手',
			path: '/pages/home/index?memberId=' + memberId.value,
			imageUrl: '/static/config/xiaoyi.png'
		}
	});
	
	const queryMemberInvite = () => {
		request({
				url: "/mini/member/queryMemberInvite",
				method: "post"
			})
			.then((data) => {
				inviteDay.value = data.inviteDay;
				inviteCount.value = data.inviteCount;
			});
	};

	onMounted(() => {
		queryMemberInvite();
	});
</script>

<template>
	<view class="content">

		<view class="cell-content">

			<view class="label">邀请新用户送2天vip时长</view>

			<view class="label" style="color: red;">上限送30天</view>
			
			<view class="label">已邀:{{ inviteCount }}人</view>
			
			<view class="label">已送:{{ inviteDay }}天</view>
		
				<van-button custom-style="
		width: 440rpx;
          margin-top: 50rpx;
          border: none;
          fontSize: 36rpx;" round type="primary" open-type="share">
					邀请好友</van-button>

			</view>

	</view>
</template>

<style src="./style.scss" lang="scss" scoped />