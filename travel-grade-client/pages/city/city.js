// pages/movies/movies-detail/movies-detail.js
const app = getApp();
const util = require('../../utils/util.js');
const api = require('../../config/api.js');
const user = require('../../utils/user.js');

// 点击'想去'按钮次数
var times_like = 0;
// 点击'点赞'按钮次数
var times_favour = 0;
// 点击'去过'按钮次数
var times_gone = 0;

Page({

    /**
     * 页面的初始数据
     */
    data: {
        city: {},
        stars: [],
        isFavour: false,
        isLike: false,
        isGone: false,
        // 是否进行操作标识
        isChange: false,
        // 城市id
        id: '',
        // 城市名称
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
        this.showCity();
    },

    // 展示城市信息方法
    showCity: function() {
        let id = this.data.id;
        let name = this.data.name;
        let that = this;

        let data = {
            id: id,
            name: name
        }

        util.request(api.GetCity, data).then((res) => {
            if (res.status == 200) {
                if (res.data.user_city_rel != null) {
                    that.setData({
                        isFavour: res.data.user_city_rel.isFavour,
                        isLike: res.data.user_city_rel.isLike,
                        isGone: res.data.user_city_rel.isGone,
                    })
                }
                that.setData({
                    city: res.data.city,
                    stars: util.converStars(res.data.city.grade)
                })
            } else {
                wx.showModal({
                    title: '错误信息',
                    content: res.msg,
                    showCancel: false,
                    success() {
                        wx.redirectTo({
                            url: '/pages/search/search',
                        })
                    }
                })
            }
        })
    },

    // 想去按钮事件
    likeBtn: function() {
        let isLike = this.data.isLike;
        this.checkLogin(0, isLike);
    },

    // 点赞按钮事件
    favourBtn: function() {
        let isFavour = this.data.isFavour;
        this.checkLogin(1, isFavour);
    },

    // 去过按钮事件
    goneBtn: function() {
        let isGone = this.data.isGone;
        this.checkLogin(2, isGone);
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