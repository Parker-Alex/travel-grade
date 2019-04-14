var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
    data: {
        provinces: [],
        currentProvince: {},
        cities: [],
        scrollLeft: 0,
        scrollTop: 0,
        goodsCount: 0,
        scrollHeight: 0
    },

    onLoad: function(options) {
        this.getProvinces(1);
    },

    // 下拉刷新
    onPullDownRefresh() {
        wx.showNavigationBarLoading() //在标题栏中显示加载
        this.getProvinces(1);
        this.getCities(this.data.currentProvince.id);
        wx.hideNavigationBarLoading() //完成停止加载
        wx.stopPullDownRefresh() //停止下拉刷新
    },

    // 获得省份列表
    getProvinces: function(pageNum) {
        let that = this;
 
        util.request(api.GetProvinces + '/' + pageNum).then(function(res) {
            that.setData({
                provinces: res.data.list,
                currentProvince: res.data.list[0]
            });

            that.getCities(that.data.currentProvince.id);
        });

    },

    // 获得省份所属城市列表
    getCities: function (provinceId) {
        let that = this;
        util.request(api.GetCitiesByProId + '/' + provinceId).then((res) => {
            that.setData({
                cities: res.data
            })
        })
    },

    // 点击省份事件
    switchPro:  function(e) {

        let that = this;
        let index = e.currentTarget.dataset.index;

        this.setData({
            currentProvince: this.data.provinces[index]
        })

        this.getCities(this.data.currentProvince.id);
    }

})