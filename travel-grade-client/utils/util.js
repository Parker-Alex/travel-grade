var api = require("../config/api.js");
var app = getApp();

// 格式化日期
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

        wx.showLoading({
            title: '请稍等...',
            mask: true
        })

        wx.request({
            url: url,
            data: data,
            method: method,
            header: {
                'Content-Type': 'application/json',
                'X-Leo-Token': wx.getStorageSync('token')
            },
            success(res) {
                if (res.statusCode === 200) {
                    wx.hideLoading();        

                    if (res.data.status === 500) {
                        wx.showModal({
                            title: '错误信息',
                            content: res.data.msg + ',请重试',
                            showCancel: false
                        })
                        // 清除登录相关内容
                        try {
                            wx.removeStorageSync('userInfo');
                            wx.removeStorageSync('token');
                        } catch (e) {
                            // Do something when catch error
                        }
                        // 跳转到登录页面
                        wx.navigateTo({
                            url: '/pages/user/index/index',
                        })
                    } else {
                        resolve(res.data);
                    } 
                } else {
                    wx.hideLoading();
                    showErrorToast("服务器异常");
                    reject(res.errMsg);
                }
            },
            fail(err) {
                showErrorToast("服务器异常");
                reject(err);
            }
        });
    });
}

// 重定向方法
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

// 显示错误信息
function showErrorToast(msg) {
    wx.showToast({
        title: msg,
        duration: 1500,
        image: '/images/icon_error.png'
    });
}

// 将星星数转化为数组
function converStars(stars) {
    // 获取评分的第一个数
    var num = stars.toString().substring(0, 1);
    var array = [];
    for (var i = 2; i <= 10; i += 2) {
        if (i <= num) {
            array.push(1);
        } else {
            array.push(0);
        }
    }
    return array;
}

module.exports = {
    formatTime,
    request,
    redirect,
    showErrorToast,
    converStars
}