// pages/city/comment/comment.js
const user = require('../../../utils/user.js');
const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const app = getApp();

Page({

    /**
     * 页面的初始数据
     */
    data: {
        clear: '',
        scrollHeight: 0,
        cityId: '',
        list: [],
        userId: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let cityId = options.id;
        let that = this;

        // 获取系统信息，设置滚动高度
        wx.getSystemInfo({
            success: function(res) {
                that.setData({
                    cityId: cityId,
                    scrollHeight: res.windowHeight
                })
            },
        })
    },

    submitForm: function(e) {
        let that = this;

        user.checkLogin().then(() => {
            let content = e.detail.value.content;
            console.log(content);
            if (content == '') {
                wx.showToast({
                    mask: true,
                    icon: 'none',
                    title: '请输入评论',
                })
                return ;
            }

            // 发送评论
            let data = {
                cityId: that.data.cityId,
                content: content
            }
            console.log(data);
            util.request(api.SendComment, data, 'POST').then((res) => {
                console.log(res);
                let list = that.data.list;
                list.push(res.data.comment);
                that.setData({
                    list: list,
                    userId: res.data.userId
                })
            })

        }).catch(() => {
            // util.showErrorToast("请先登录");
            wx.showModal({
                title: '提示',
                content: '请先登录',
                success(res) {
                    if (res.confirm) {
                        wx.navigateTo({
                            url: '/pages/system/login/login',
                        })
                    } else if (res.cancel) {
                        return ;
                    }
                }
            })
        })
        that.setData({
            clear: ''
        })
    }
})