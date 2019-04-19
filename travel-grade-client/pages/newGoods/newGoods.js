var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      this.getOtherUser('190221865XWCADWH');
  },

    // 得到用户信息方法
    getOtherUser: function (id) {
        let that = this;
        util.request(api.GetOtherUser + '/' + id).then((res) => {
            console.log(res);
            that.setData({
                user: res.data.user,
                fans: res.data.fans,
                comments: res.data.comments,
                recommends: res.data.recommends,
                gone_cities: res.data.gone_cities,
                like_cities: res.data.like_cities
            })
        })
    },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  }
})