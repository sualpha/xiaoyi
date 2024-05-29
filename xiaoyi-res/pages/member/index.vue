<script setup name="member">
	import {
		ref,
		computed
	} from 'vue';
	import {
		onMounted
	} from '@/hooks/onMounted';
	import {
		useState
	} from '@/hooks/useState';
	import moment from 'moment';
	import {
		appName
	} from '@/config';
	import {
		obtainIsVip
	} from '@/hooks/obtainIsVip';
	import {
		request
	} from "@/request/index.js";


import { onShareAppMessage } from '@/hooks/onShareAppMessage';

onShareAppMessage();
	const {
		avatar,
		memberId,
		nickName,
		registerTime
	} = useState();

	const defaultAvatar = ref('../../static/images/icon_avatar.png');
	const formatDuration = computed(() => moment(moment().valueOf()).diff(moment(registerTime.value), 'days') + 1);
	const isVip = ref(false);
	const vipExpireTime = ref('');
	const ledgerDays = ref(0);
	const ledgerCount = ref(0);


	const onUserInfoClick = () => {

		uni.navigateTo({
			url: '/pages/member-info/index'
		});

	};

	const onQuickVoiceClick = () => {

		uni.navigateTo({
			url: '/pages/quick-voice/index'
		});

	};

	const onOpenVipClick = () => {

		uni.navigateTo({
			url: '/pages/vip/index'
		});

	};
	
	const onMemberInviteClick = () => {

		uni.navigateTo({
			url: '/pages/member-invite/index'
		});

	};
	
	
	const onMemberStatistics = () => {
		request({
				url: "/mini/member/statistics",
				method: "post"
			})
			.then((data) => {
				ledgerDays.value = data.ledgerDays;
				ledgerCount.value = data.ledgerCount;
			});
	};

	onMounted(() => {
		onMemberStatistics();
		obtainIsVip().then(result => {
			isVip.value = result.isVip;
			vipExpireTime.value = result.vipExpireTime;
		})
	});
</script>

<template>

	<view class="content">

		<view class="user-info" @click="onUserInfoClick">

			<image class="avatar" v-if="avatar" :src="avatar" />
			<image class="avatar" v-else :src="defaultAvatar" />

			<view class="wrap">
				<view class="name-content">
					<view class="name">{{ nickName }}</view>
					<view class="time">(ID:{{ memberId }})</view>
				</view>

				<view class="day-content">
					<view class="day-wrap">
						<text class="time">{{ formatDuration }}</text>
						<text class="time">使用天数</text>
					</view>

					<view class="day-wrap">
						<text class="time">{{ ledgerDays }}</text>
						<text class="time">记账天数</text>
					</view>

					<view class="day-wrap">
						<text class="time">{{ ledgerCount }}</text>
						<text class="time">记账笔数</text>
					</view>
				</view>
			</view>

			<image class="more" src="../../static/svgs/icon_right_gray.svg" />

		</view>

		<view class="vip-box">
			<view>
				<view class="vip-title">{{appName}} VIP</view>
				<view class="vip-desc">
					{{ isVip ? '到期时间：' + vipExpireTime : '开通会员可享语音记账等后续更多功能' }}
				</view>
			</view>
			<view class="vip-btn" @click="onOpenVipClick" v-if="!isVip"></view>
		</view>

		<button class="cell" open-type="feedback">

			<image class="icon" src="../../static/svgs/icon_feedback.svg" />

			<view class="label">意见反馈</view>

			<image class="more" src="../../static/svgs/icon_right_gray.svg" />

		</button>

		<view class="divider" v-if="isVip"/>

		<button class="cell" @click="onQuickVoiceClick" v-if="isVip">

			<image class="icon" src="../../static/svgs/icon_voice.svg" />

			<view class="label">快捷语音记账</view>

			<image class="more" src="../../static/svgs/icon_right_gray.svg" />

		</button>

		<view class="divider" />

		<button class="cell" @click="onMemberInviteClick">

			<image class="icon" src="../../static/svgs/icon_share.svg" />

			<view class="label">推荐好友送vip时长</view>

			<image class="more" src="../../static/svgs/icon_right_gray.svg" />

		</button>
		
		<view class="divider" />
		
		<button class="cell" open-type="contact">
		
			<image class="icon" src="../../static/svgs/icon_customer.svg" />
		
			<view class="label">小易助手客服</view>
		
			<image class="more" src="../../static/svgs/icon_right_gray.svg" />
		
		</button>

		<view class="divider" />
	</view>
</template>

<style src="./style.scss" lang="scss" scoped />