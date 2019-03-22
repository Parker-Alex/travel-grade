// pages/city/comment/comment.js
const user = require('../../../utils/user.js');
const util = require('../../../utils/util.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        clear: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let cityId = options.id;
    },

    submitForm: function(e) {
        let that = this;
        user.checkLogin().then(() => {
            let from = e.detail.value;
            console.log(from.comment);
            if (from.comment == '') {
                wx.showToast({
                    mask: true,
                    title: '请输入评论',
                })
                return ;
            }
        }).catch(() => {
            util.showErrorToast("请先登录");
        })
        that.setData({
            clear: ''
        })
    }
})