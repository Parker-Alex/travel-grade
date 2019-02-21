// pages/system/login/login.js
const util = require('../../../utils/util.js');
const user = require('../../../utils/user.js');
const api = require('../../../config/api.js');
const app = getApp();

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

    },

    wxLogin(e) {
        // util.showErrorToast('暂时不支持该功能');
        if (e.detail.userInfo == undefined) {
            app.globalData.hasLogin = false;
            util.showErrorToast('微信登录失败');
            return;
        }
        
        user.checkLogin().catch(() => {

            user.loginByWx(e.detail.userInfo).then(res => {
                // 登录成功
                app.globalData.hasLogin = true;               
                wx.navigateBack({
                    delta: 1
                });
            }).catch((err) => {
                // 登录失败
                app.globalData.hasLogin = false;
                console.log(err);
                util.showErrorToast('微信登录失败');
            });
        });
    },

    accountLogin() {
        wx.navigateTo({
            url: '/pages/system/accountLogin/accountLogin',
        });
    }
})