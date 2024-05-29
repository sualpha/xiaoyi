import { onShareAppMessage as onShareApp } from "@dcloudio/uni-app";

export const onShareAppMessage = () => onShareApp(() => {

    return {
        title: '欢迎来到小易记账助手',
        path: '/pages/home/index',
        imageUrl: '/static/config/xiaoyi.png'
    };

});