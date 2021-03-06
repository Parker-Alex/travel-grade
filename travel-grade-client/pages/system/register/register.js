const api = require('../../../config/api.js');
const check_phone = require('../../../utils/check_phone.js');
const app = getApp();

Page({

    /**
     * 页面的初始数据
     */
    data: {
        username: '',
        password: '',
        confirmPassword: '',
        mobile: '',
        code: ''
    },

    // 注册按钮事件
    register: function() {
        let that = this;

        // 检验用户名和密码
        if (this.data.username.length < 3 || this.data.password.length < 3) {
            wx.showModal({
                title: '错误信息',
                content: '用户名或密码不能少于6位',
                showCancel: false
            });
            return;
        }

        // 确认密码
        if (this.data.password != this.data.confirmPassword) {
            wx.showModal({
                title: '错误信息',
                content: '两次密码不一致',
                showCancel: false
            });
            return;
        }

        // 检验手机号和验证码
        if (this.data.mobile.length == 0 || this.data.code.length == 0) {
            wx.showModal({
                title: '错误信息',
                content: '手机号或验证码不能为空',
                showCancel: false
            });
            return;
        }

        // 校验手机号
        if (!check_phone.isValidPhoneNumber(this.data.mobile)) {
            wx.showModal({
                title: '错误信息',
                content: '手机号格式不正确',
                showCancel: false
            });
            return false;
        }

        // 获得wx码
        wx.login({
            success(res) {
                if (!res.code) {
                    wx.showModal({
                        title: '错误信息',
                        content: '注册失败',
                        showCancel: false
                    });
                }
                // 开始注册
                that.startRegister(res.code);
            }
        });
    },

    // 正式开始注册方法
    startRegister: function(wxCode) {
        let that = this;

        wx.showLoading({
            title: '请稍等...',
            mask: true
        })

        // 发送请求给后台
        wx.request({
            url: api.Register,
            data: {
                username: that.data.username,
                password: that.data.password,
                mobile: that.data.mobile,
                code: that.data.code,
                wxCode: wxCode
            },
            method: 'POST',
            header: {
                'content-type': 'application/json'
            },
            success(res) {
                wx.hideLoading();
                console.log(res);
                if (res.data.status == 200) {
                    // 设置登录状态
                    app.globalData.hasLogin = true;
                    // 将用户信息存入微信缓存
                    wx.setStorageSync('userInfo', res.data.data.userInfo);
                    // 将token存入微信缓存
                    wx.setStorage({
                        key: 'token',
                        data: res.data.data.token,
                        // 跳转用户个人页面
                        success() {
                            wx.switchTab({
                                url: '/pages/user/index/index',
                            })
                        }
                    })
                } else if (res.statusCode === 500) {
                    wx.showModal({
                        title: '错误信息',
                        content: '服务器错误',
                        showCancel: false
                    });
                } else {
                    wx.showModal({
                        title: '错误信息',
                        content: res.data.msg,
                        showCancel: false
                    });
                }
            },
            fail() {
                wx.hideLoading();
                util.showErrorToast('连接超时');
            }
        })
    },

    // 获取验证码
    getCode: function() {
        let that = this;

        if (this.data.mobile.length == 0) {
            wx.showModal({
                title: '错误信息',
                content: '手机号不能为空',
                showCancel: false
            });
            return false;
        }

        if (!check_phone.isValidPhoneNumber(this.data.mobile)) {
            wx.showModal({
                title: '错误信息',
                content: '手机号格式不正确',
                showCancel: false
            });
            return false;
        }

        wx.showLoading({
            title: '发送中...',
            mask: true
        });

        wx.request({
            url: api.RegisterCaptcha,
            data: {
                mobile: that.data.mobile
            },
            method: 'POST',
            header: {
                'content-type': 'application/json'
            },
            success(res) {
                wx.hideLoading();
                if (res.data.status === 200) {
                    wx.showToast({
                        title: '成功获取验证码',
                        duration: 1500,
                        icon: 'success'
                    });
                    // 将获取的验证码显示
                    that.setData({
                        code: res.data.data.code
                    });
                } else if (res.statusCode === 500) {
                    wx.showModal({
                        title: '错误信息',
                        content: '服务器错误',
                        showCancel: false
                    });
                } else {
                    wx.showModal({
                        title: '错误信息',
                        content: res.data.msg,
                        showCancel: false
                    });
                }
            },
            fail() {
                wx.hideLoading();
                util.showErrorToast('连接超时');
            }
        })
    },

    clearInput: function (e) {
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
            case 'clear-confirm-password':
                this.setData({
                    confirmPassword: ''
                });
                break;
            case 'clear-mobile':
                this.setData({
                    mobile: ''
                });
                break;
            case 'clear-code':
                this.setData({
                    code: ''
                });
                break;
        }
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

    bindConfirmPasswordInput: function (e) {
        this.setData({
            confirmPassword: e.detail.value
        });
    },

    bindMobileInput: function(e) {
        this.setData({
            mobile: e.detail.value
        });        
    },

    bindCodeInput: function(e) {
        this.setData({
            code: e.detail.value
        });
    }

})