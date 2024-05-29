<script setup name="quick-voice">
	import {
		ref
	} from 'vue';
	import {
		onMounted
	} from '@/hooks/onMounted';

	import {
		request
	} from "@/request/index.js";

	const apikey = ref("");
	const showIosPopup = ref(false);

	const queryApikey = () => {
		request({
			url: "/mini/member/queryApikey",
			method: "post"
		}).then(result => {
			apikey.value = result;
		})
	}

	const generateApikey = () => {
		request({
			url: "/mini/member/createApikey",
			method: "post"
		}).then(result => {
			apikey.value = result;
		})
	}
	const showIosClick = () => {
		showIosPopup.value = true;
	}
	const onIosPopupClose = () => {
		showIosPopup.value = false;
	}
	const copyButtonClick = () => {
		const url ='https://xiaoyi.cloudyi.tech/open/toApikey?apikey='+apikey.value;
		uni.setClipboardData({
			data: url,
			showToast: true,
			success: function() {
				console.log('setClipboardDataSuccess');
			}
		});
	}
 /* const copyButtonClick = () => {
    uni.setClipboardData({
      data: apikey.value,
      showToast: true,
      success: function() {
        console.log('setClipboardDataSuccess');
      }
    });
    uni.downloadFile({
      url: 'http://cdn.cloudyi.tech/xiaoyi/config/xiaoyi.shortcut?e=1694696766&token=39Q-fULgluR1XBPf_1tYAdkXVIAwDn6YjHrcgunw:JbjrelK0V8qWBtWLwNMD6-T5vOw=',
      success: (res) => {
        if (res.statusCode === 200) {
          fileSystemManager.open({
            filePath: res.tempFilePath,
            success(res) {
              console.log('文件打开成功')
              console.log(res.fd)
            },
            fail(res) {
              console.log('文件打开失败')
              console.log(res.fd)
            }
          })
        }
      }
    });
  } */
	onMounted(() => {
		queryApikey()
	});
</script>

<template>
	<view class="content">

		<button class="cell" @click="showIosClick">

			<image class="icon" src="../../static/svgs/icon_ios.svg" />

			<view class="label">使用苹果快捷指令记账</view>

			<image class="more" src="../../static/svgs/icon_right_gray.svg" />

		</button>

		<van-popup :show="showIosPopup" round custom-style="height: 400rpx;width:600rpx" closeable
			@close="onIosPopupClose">

			<view class="popup">
				<view class="popup-title">小易api密钥</view>
<!--				<textarea :value="apikey" style="height: 200rpx;width:600rpx;pointer-events: none;"></textarea>-->
				 <van-field type="textarea" custom-style="user-select;" autosize="{ maxHeight: 200, minHeight: 100 }" readonly="true" input-align="center"
					:value="apikey" v-if="showIosPopup" />
				<button class="popup-button" v-if="apikey===''" @click="generateApikey">生成小易api密钥</button>
				<button class="popup-button" v-else @click="copyButtonClick">复制且在浏览器中打开</button>
			</view>

		</van-popup>

	</view>



</template>

<style src="./style.scss" lang="scss" scoped />