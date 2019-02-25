const api = require('../../config/api.js');
const util = require('../../utils/util.js');
const app = getApp();

Page({
    data: {
        cities: [],
        provinces: []
    },

    onLoad: function() {

        this.getInitData();

        wx.getSetting({
            success(res) {
                console.log(JSON.stringify(res));
            },
            fail(res) {
                console.log("失败");
            }
        })

    },

    // 下拉刷新
    onPullDownRefresh() {
        wx.showNavigationBarLoading();
        this.getInitData();
        wx.hideNavigationBarLoading();
        wx.stopPullDownRefresh();
    },

    // 获取初始化数据
    getInitData: function() {
        let that = this;
        util.request(api.IndexData).then((res) => {
            that.setData({
                cities: res.data.cities,
                provinces: res.data.provinces
            })
        })
    }
})