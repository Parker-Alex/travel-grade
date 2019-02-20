var api = require("../config/api.js");
var app = getApp();

function formatTime(date) {
    var year = date.getFullYear();
    var month = date.getMouth() + 1;
    var day = date.getDate();

    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();

    return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, day].map(formatNumber).join(':');
}

function formatNumber(n) {
    n = n.toString();
    return n[1] ? n : '0' + n;
}

/**
 * 封封微信的wx.request
 */
function request(url, data = {}, method = 'GET') {
    return new Promise(function(resolve, reject) {
        wx.request({
            url: url,
            data: data,
            method: method,
            header: {
                'Content-Type': 'application/json',
                'X-Leo-Token': wx.getStorageSync('token')
            },
            success(res) {
                console.log('util.js 调用后台成功')
                console.log(res);
                if (res.statusCode === 200) {
                    if (res.data.status === 500) {
                        // 清除登录相关内容
                        try {
                            wx.removeStorageSync('userInfo');
                            wx.removeStorageSync('token');
                        } catch (e) {
                            // Do something when catch error
                        }
                        // 切换到登录页面
                        wx.navigateTo({
                            url: '/pages/system/login/login'
                        });
                    } else {
                        resolve(res.data);
                    } 
                } else {
                    reject(res.errMsg);
                }
            },
            fail(err) {
                reject(err);
            }
        });
    });
}

function redirect(url) {

    // 先判断页面是否需要登录
    if (flase) {
        wx.redirectTo({
            url: '/pages/system/login/login',
        });
        return false;
    } else {
        wx.redirectTo({
            url: url,
        });
    }
}

function showErrorToast(msg) {
    wx.showToast({
        title: msg,
        duration: 1500,
        image: '/images/icon_error.png'
    });
}

module.exports = {
    formatTime,
    request,
    redirect,
    showErrorToast
}