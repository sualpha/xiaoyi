import { request } from "@/request/index.js";

export const obtainIsVip = () => {
    return new Promise((resolve, reject) => {
        request({
            url: "/mini/member/isVip",
            method: "post"
        }).then(result => {
            resolve(result); // 使用resolve返回异步结果
        }).catch(error => {
            reject(error); // 处理可能的错误
        });
    });
};
