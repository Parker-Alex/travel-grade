// pages/movies/movies-detail/movies-detail.js
const app = getApp();
const util = require('../../utils/util.js');
const api = require('../../config/api.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        city: {}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let id = options.id;
        let value = options.value;
        let that = this;

        if (id == undefined) {
            id = null;
        }
        if (value == undefined) {
            value = null;
        }

        let data = {
            id: id,
            name: value
        }

        util.request(api.GetCity, data).then((res) => {
            console.log(res);
            if (res.status == 200) {
                that.setData({
                    city: res.data
                })
            } else {
                wx.showModal({
                    title: '错误信息',
                    content: res.msg,
                    showCancel: false,
                    success() {
                        wx.redirectTo({
                            url: '/pages/search/search',
                        })
                    }
                })
            }
        })
    },

    
})