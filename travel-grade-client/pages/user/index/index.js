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

    onLoad: function(options) {

    },

    onShow: function(options) {
        if (app.globalData.hasLogin) {
            let userInfo = wx.getStorageSync('userInfo');
            this.setData({
                userInfo: userInfo,
                hasLogin: true
            });
        }
    },

    // 跳转我的推荐页面
    goRecommend: function(e) {
        // if (this.data.hasLogin) {
            wx.navigateTo({
                url: '/pages/user/recommendList/recommendList',
            })
        // } else {
        //     wx.navigateTo({
        //         url: '/pages/system/login/login',
        //     })
        // }
    },

    // 跳转我的去去城市页面
    goGone: function(e) {
        wx.navigateTo({
            url: '/pages/user/goneList/goneList',
        })
    },

    // 跳转我的想去城市页面
    goLike: function(e) {
        wx.navigateTo({
            url: '/pages/user/likeList/likeList',
        })
    },

    // 跳转我的点赞城市页面
    goFavour: function(e) {
        wx.navigateTo({
            url: '/pages/user/favourList/favourList',
        })
    },

    // 跳转我的评论页面
    goComment: function(e) {
        wx.navigateTo({
            url: '/pages/user/commentList/commentList',
        })
    },

    // 跳转我的粉丝页面
    goFans: function() {
        wx.navigateTo({
            url: '/pages/user/fansList/fansList',
        })
    },

    // 跳转我的关注页面
    goFollows: function() {
        wx.navigateTo({
            url: '/pages/user/followsList/followsList',
        })
    },

    // 跳转登录界面
    goLogin() {
        if (!this.data.hasLogin) {
            wx.navigateTo({
                url: '/pages/system/login/login',
            });
        }
    },

    // 注销
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
    },

    // goOrder() {
    //     wx.navigateTo({
    //         url: "/pages/user/order/order"
    //     });
    // }
})