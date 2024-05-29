import { memberAuth } from '@/hooks/memberAuth';
import { useState } from '@/hooks/useState';

const {
  host
} = useState();

let ajaxTimes = 0;

export const request = (params) => {
  ajaxTimes++;
  //显示加载中效果
  uni.showLoading({
    title: "加载中",
  });
  const token = uni.getStorageSync('token');
  const header = {
    "XiaoYiToken": token || ""
  };
  function performRequest(retryCount) {
    return new Promise((resolve, reject) => {
      uni.request({
        ...params,
        header: header,
        url: host.value + params.url,
        success: (result) => {
          if (result.data.code === 200) {
            resolve(result.data.data);
          } else if (result.data.code === 401) {
            if (retryCount > 0) {
              memberAuth()
                .then(() => performRequest(retryCount - 1))
                .catch(() => reject(result.data.message));
            } else {
              reject(result.data.message);
            }
          } else {
            reject(result.data.message);
          }
        },
        fail: (err) => {
          reject(err);
        },
        complete: () => {
          ajaxTimes--;
          if (ajaxTimes === 0) {
            uni.hideLoading();
          }
        }
      });
    });
  }

  return performRequest(3); // Retry up to 3 times
};
