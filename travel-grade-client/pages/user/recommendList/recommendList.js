// pages/user/recommend/recommend.js
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

    },

    // 跳转推荐城市页面
    recommendCity: function(e) {
        wx.navigateTo({
            url: '/pages/user/recommend/recommend',
        })
    }
})