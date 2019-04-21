// pages/user/recommend/recommend.js
const app = getApp();
const util = require('../../../utils/util.js');
const user = require('../../../utils/user.js');
const api = require('../../../config/api.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        cities: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getRecommendCities();
    },

    // 得到推荐城市列表
    getRecommendCities: function() {
        let that = this;
        util.request(api.UserRecommendCities).then((res) => {
            console.log(res);
            that.setData({
                cities: res.data
            })
        })
    },

    // 跳转推荐城市页面
    recommendCity: function(e) {
        wx.navigateTo({
            url: '/pages/user/recommend/recommend',
        })
    }
})