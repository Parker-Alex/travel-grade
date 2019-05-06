const api = require('../../config/api.js');
const util = require('../../utils/util.js');
const app = getApp();

Page({
    data: {
        hotCities: [],
        commendCities: [],
        favourCities: [],
        goneCities: [],
        gradeCities: [],
        likeCities: [],
        provinces: [],
        types: []
    },

    onLoad: function() {
        console.log("onLoad");
        

        // TODO 获取本地位置信息

    },

    onShow: function() {
        console.log("onshow");
        this.getInitData();
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
            console.log(res);
            that.setData({
                hotCities: res.data.hotCities,
                commendCities: res.data.commendCities,
                favourCities: res.data.favourCities,
                goneCities: res.data.goneCities,
                gradeCities: res.data.gradeCities,
                likeCities: res.data.likeCities,
                provinces: res.data.provinces,
                types: res.data.types
            })
        })
    }
})