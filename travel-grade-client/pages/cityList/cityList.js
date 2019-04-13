const api = require('../../config/api.js');
const util = require('../../utils/util.js');
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    cityList: [],
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
    let index = options.index;
    let that = this;
    util.request(api.CityList + '/' + index + '/' + index).then((res) => {
        that.setData({
            cityList: res.data
        })
    })
  },

})