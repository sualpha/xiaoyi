<script setup name="member-info">
	import {
		ref
	} from 'vue';
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
		avatar,
		nickName,
		memberId,
		changeMemberInfo,
		setNickName,
		host
	} = useState();

	const defaultAvatar = ref('../../static/images/icon_avatar.png');
	// 为了防止提交的时候再次触发提交
	const isSubmitting = ref(false);

	const onAvatarClick = (e) => {

		avatar.value = e.detail.avatarUrl;

	};

	const onInputChange = (e) => {
		nickName.value = e.detail.value;
	};

	const onSubmitButtonClick = () => {
		if (isSubmitting.value) {
			return;
		}
		if (!avatar.value) {
			uni.showToast({
				title: '请选择头像',
				icon: 'none'
			});
			return;
		}
		if (!nickName.value) {
			uni.showToast({
				title: '请选择输入昵称',
				icon: 'none'
			});
			return;
		}
		uni.showLoading({
			title: '保存中',
			mask: true
		});
		isSubmitting.value = true;
		let promise;
		if (avatar.value.slice(0, 10) == "http://tmp" || avatar.value.slice(0, 9) == "wxfile://") {
			promise = uploadFile();
		} else {
			promise = updateMemberInfo();
		}
		promise
			.then(() => {
				// 继续执行其他操作
				uni.hideLoading();
				isSubmitting.value = false;
				uni.showToast({
					title: '保存成功',
					icon: 'success',
					duration: 1000
				});
				uni.navigateBack();
			})
			.catch((error) => {
				console.error("Error:", error);
				// 处理错误情况
				uni.hideLoading();
				isSubmitting.value = false;
			});

	};

	const updateMemberInfo = () => {
		const data = {
			nickName: nickName.value,
		};
		return new Promise((resolve, reject) => {
			return request({
					url: "/mini/member/update",
					method: "post",
					data
				})
				.then((result) => {
					setNickName({
						nickName: nickName.value
					});
					resolve();
				})
				.catch(error => {
					console.error("Error:", error);
					reject(error);
				});
		})
	};

	const uploadFile = () => {
		const token = uni.getStorageSync('token');
		const data = {
			nickName: nickName.value
		};
		return new Promise((resolve, reject) => {
			uni.uploadFile({
				url: host.value + '/mini/member/uploadHead',
				filePath: avatar.value,
				fileType: 'image',
				header: {
					"content-type": "multipart/form-data",
					"XiaoYiToken": token || ""
				},
				name: 'file',
				formData: data,
				success: res => {
					const result = JSON.parse(res.data);
					changeMemberInfo({
						nickName: nickName.value,
						avatar: result.data
					});
					resolve();
				},
				fail: error => {
					reject(error);
				}
			});
		});
	};

	onMounted(() => {


	});
</script>

<template>
	<view class="content">

		<view class="cell-content">

			<view class="cell">

				<view class="label">头像</view>

				<view class="value">

					<button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onAvatarClick">

						<image :src="avatar || defaultAvatar" />

					</button>

				</view>

			</view>

			<view class="cell">

				<view class="label">昵称</view>

				<view class="value">

					<input class="input" :value="nickName" type="nickname" placeholder="请输入昵称" @blur="onInputChange"
						@input="onInputChange" />

				</view>

			</view>

		</view>

		<button class="button" hover-class="default-hover-class" hover-stay-time="100" @click="onSubmitButtonClick">

			保 存

		</button>

	</view>
</template>

<style src="./style.scss" lang="scss" scoped />