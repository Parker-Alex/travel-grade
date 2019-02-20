var api = require('../../../config/api.js');
var util = require('../../../utils/util.js');
var user = require('../../../utils/user.js');
var app = getApp();

Page({
    data: {
        username: '',
        password: '',
        code: '',
        loginErrorCount: 0
    },
    onLoad: function(options) {
        // 页面初始化 options为页面跳转所带来的参数
        // 页面渲染完成

    },

    bindUsernameInput: function(e) {
        this.setData({
            username: e.detail.value
        });
    },

    bindPasswordInput: function(e) {
        this.setData({
            password: e.detail.value
        });
    },

    clearInput: function(e) {
        switch (e.currentTarget.id) {
            case 'clear-username':
                this.setData({
                    username: ''
                });
                break;
            case 'clear-password':
                this.setData({
                    password: ''
                });
                break;
        }
    },

    accountLogin: function() {
        var that = this;

        if (this.data.username.length < 1 || this.data.password < 1) {
            wx.showModal({
                title: '错误信息',
                content: '请输入用户或密码',
                showCancel: false
            });
            return false;
        }

        wx.showLoading({
            title: '正在登录...',
            mask: true
        });

        wx.request({

            url: api.LoginByAccount,
            data: {
                username: that.data.username,
                password: that.data.password
            },
            method: 'POST',
            header: {
                'content-type': 'application/json'
            },
            success(res) {
                wx.hideLoading();
                if (res.data.status === 200) {
                    app.globalData.hasLogin = true;
                    wx.setStorageSync("userInfo", res.data.data.userInfo);
                    wx.setStorage({
                        key: 'token',
                        data: res.data.data.token,
                        success(res) {
                            wx.switchTab({
                                url: '/pages/user/index/index',
                            });
                        }
                    });
                } else {
                    app.globalData.hasLogin = false;
                    util.showErrorToast(res.data.msg);
                }
            },
            fail() {
                wx.hideLoading();
                util.showErrorToast('连接超时');
            }
        })
    }
})