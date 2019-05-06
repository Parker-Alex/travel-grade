const app = getApp();
const util = require('../../../utils/util.js');
const user = require('../../../utils/user.js');
const api = require('../../../config/api.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        follows: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getFollows();
    },

    // 得到用户评论
    getFollows: function () {
        let that = this;
        util.request(api.UserFollows).then((res) => {
            console.log(res.data);

            that.setData({
                follows: res.data
            })
        })
    }
})