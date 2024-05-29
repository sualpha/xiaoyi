import { request } from "@/request/index.js";
import { useState } from '@/hooks/useState';

export const memberAuth = () => {
    return new Promise((resolve, reject) => {
        const { setMemberInfo } = useState();

        uni.login({
            provider: 'weixin'
        }).then(({ code }) => {
            request({
                url: "/mini/member/auth?code=" + code
            }).then(result => {
                uni.setStorageSync("token", result.token);
                request({
                    url: "/mini/member/queryMemberDetail",
                    method: "post"
                }).then(memberInfo => {
                    setMemberInfo({
                        memberId: memberInfo.id,
                        nickName: memberInfo.nickName,
                        avatar: memberInfo.avatarUrl,
						registerTime: memberInfo.createTime
                    });
                    resolve(); // 成功时 resolve
                }).catch(error => {
                    console.log("Error 111:", error);
                    reject(error); // 失败时 reject
                });
            }).catch(error => {
                console.log(error);
                reject(error); // 失败时 reject
            });
        });
    });
};
