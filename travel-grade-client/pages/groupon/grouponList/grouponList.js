// pages/groupon/grouponList/grouponList.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    grouponList: [],
    page: 1,
    size: 10,
    count: 0,
    scrollTop: 0,
    showPage: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
  },

})