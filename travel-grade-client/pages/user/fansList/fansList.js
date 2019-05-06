const app = getApp();
const util = require('../../../utils/util.js');
const user = require('../../../utils/user.js');
const api = require('../../../config/api.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        fans: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getFans();
    },

    // 得到用户评论
    getFans: function () {
        let that = this;
        util.request(api.UserFans).then((res) => {
            console.log(res.data);

            that.setData({
                fans: res.data
            })
        })
    }
})