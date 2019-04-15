// pages/user/index/index.js
const app = getApp();
const util = require('../../../utils/util.js');
const user = require('../../../utils/user.js');
const api = require('../../../config/api.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {
            nickName: '点击登录',
            avatarUrl: 'http://yanxuan.nosdn.127.net/8945ae63d940cc42406c3f67019c5cb6.png'
        },
        hasLogin: false
    },

    onLoad: function (options) {

    },

    onShow: function (options) {
        if (app.globalData.hasLogin) {
            let userInfo = wx.getStorageSync('userInfo');
            this.setData({
                userInfo: userInfo,
                hasLogin: true
            });
        }
    },

    goLogin() {
        if (!this.data.hasLogin) {
            wx.navigateTo({
                url: '/pages/system/login/login',
            });
        }
    },

    loginout() {
        let that = this;

        wx.showModal({
            title: '提示',
            content: '退出登录?',
            confirmColor: '#b4282d',
            success(res) {
                if (res.confirm) {
                    util.request(api.LoginOut, {}, 'POST').then((res) => {
                        app.globalData.hasLogin = false;
                        // 清缓存
                        wx.removeStorageSync('userInfo');
                        wx.removeStorageSync('token');
                        // 重新启动
                        wx.reLaunch({
                            url: '/pages/index/index'
                        });
                    });
                } else {
                    return;
                }
            }
        })

        
    }    
})