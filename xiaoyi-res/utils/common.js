import { request } from "../request/index.js";

export const pageModule = {
    queryPageModule(pageId) {
        return new Promise((resolve, reject) => {
            pageModule._queryPageModule(pageId, resolve, reject);
        })
    },
    _queryPageModule(pageId, resolve, reject) {
        const userInfo = wx.getStorageSync('userInfo')
        const data = {
            lang: userInfo.language,
            pageId: pageId,
        };
        request({ url: "/page/queryPageModule", method: "post", data }).then((result) => {
            if (result) {
                resolve(result.data.pageModuleMap);
            }
        })
    }
}

module.exports = {
    pageModule
}
