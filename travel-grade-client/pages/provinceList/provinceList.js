const api = require('../../config/api.js');
const util = require('../../utils/util.js');
const app = getApp();

Page({

    /**
     * 页面的初始数据
     */
    data: {
        provinceList: [],
        pages: 0
        /**
         * 
        scrollTop: 0,
        showPage: false,
        hasNextPage: false,
        hasPreviousPage: false
        // 当前页码
        pageNum: 1,
        // 分页大小
        size: 10,
        // 总数据数
        total: 0,
         */
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        this.getProvinces(1);
    },

    // 获得城市信息方法
    getProvinces: function(pageNum) {
        let index = this.data.index;
        let list = this.data.provinceList;
        let that = this;

        util.request(api.GetProvinces + '/' + pageNum).then((res) => {
            console.log(res);
            if (res.data.list.length <= 0) {
                wx.showToast({
                    title: '没有更多了......',
                    duration: 1500,
                    icon: 'none'
                })
            }
            that.setData({
                provinceList: list.concat(res.data.list),
                pageNum: pageNum,
                pages: res.data.pages
            })
        })
    },

    // 下拉刷新
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading();
        // 初始化数据
        this.setData({
            provinceList: []
        })
        this.getProvinces(1);
        wx.hideNavigationBarLoading();
        wx.stopPullDownRefresh();
    },

    // 上拉刷新
    onReachBottom: function() {
        let pageNum = this.data.pageNum;
        let pages = this.data.pages;
        
        // 判断是否为最后一页
        if (pageNum + 1 > pages) {
            wx.showToast({
                title: '已经到极限了...',
                duration: 1000,
                icon: 'none'
            })
            return ;
        }
        this.getProvinces(pageNum + 1);
    }
})