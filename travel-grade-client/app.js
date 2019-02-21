//app.js
App({
    onLaunch: function() {
        wx.removeStorageSync('userInfo');
        wx.removeStorageSync('token');
    },
    globalData: {
        hasLogin: false
    }
})