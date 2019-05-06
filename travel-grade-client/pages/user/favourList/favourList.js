const app = getApp();
const util = require('../../../utils/util.js');
const user = require('../../../utils/user.js');
const api = require('../../../config/api.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        cities: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getFavourCities();
    },

    // 得到去过城市列表
    getFavourCities: function () {
        let that = this;
        util.request(api.UserFavourCities).then((res) => {
            console.log(res);
            that.setData({
                cities: res.data
            })
        })
    },
})