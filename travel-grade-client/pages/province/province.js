// pages/movies/movies-detail/movies-detail.js
const app = getApp();
const util = require('../../utils/util.js');
const api = require('../../config/api.js');
const user = require('../../utils/user.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        province: {},
        cities: [],
        stars: [],
        // 是否进行操作标识
        isChange: false,
        // 省份id
        id: '',
        // 省份名称
        name: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        let id = options.id;
        let name = options.value;

        if (id == undefined) {
            id = null;
        }
        if (name == undefined) {
            name = null;
        }

        this.setData({
            id: id,
            name: name
        })

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
        this.showProvince();
    },

    // 展示城市信息方法
    showProvince: function() {
        let id = this.data.id;
        let name = this.data.name;
        let that = this;

        let data = {
            id: id,
            name: name
        }

        util.request(api.GetProvince, data, 'POST').then((res) => {
            console.log(res);
            that.setData({
                province: res.data.province,
                stars: util.converStars(res.data.province.grade),
                cities: res.data.cities
            })
        })
    },

    // 检查是否登录
    checkLogin: function(index, flag) {
        let that = this;

        user.checkLogin().then(() => {
            if (index === 0) {
                that.setData({
                    isLike: !flag
                })
            }
            if (index === 1) {
                that.setData({
                    isFavour: !flag
                })
            }
            if (index === 2) {
                that.setData({
                    isGone: !flag
                })
            }
            that.setData({
                isChange: true
            })
        }).catch(() => {
            wx.navigateTo({
                url: '/pages/system/login/login',
            })
        })
    },

    // 更新城市方法
    updateCity: function() {
        let that = this;
        let data = {};

        data = {
            isFavour: that.data.isFavour,
            isLike: that.data.isLike,
            isGone: that.data.isGone,
            cityId: that.data.city.id
        }
        console.log(data);

        util.request(api.UpdateUserCityRel, data, 'POST').then((res) => {
            that.setData({
                isChange: false
            })
        })
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {
        // 如果用户进行了操作才更新
        if (this.data.isChange) {
            this.updateCity();
        }
    },

})