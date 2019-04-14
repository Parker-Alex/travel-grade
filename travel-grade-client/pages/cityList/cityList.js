const api = require('../../config/api.js');
const util = require('../../utils/util.js');
const app = getApp();

Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 条件标识
        index: 0,
        cityList: [],
        scrollTop: 0,
        showPage: false,
        hasNextPage: false,
        hasPreviousPage: false

        /**
         * 
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
        let index = options.index;
        this.setData({
            index: index
        })

        this.getCities(1);
    },

    // 获得城市信息方法
    getCities: function(pageNum) {
        let index = this.data.index;
        let that = this;
        util.request(api.CityList + '/' + index + '/' + pageNum).then((res) => {
            console.log(res);
            that.setData({
                showPage: true,
                cityList: res.data.list,
                pageNum: pageNum,
                hasNextPage: res.data.hasNextPage,
                hasPreviousPage: res.data.hasPreviousPage
            })
        })
    },

    // 上一页方法
    prevPage: function() {
        let pageNum = this.data.pageNum;

        if (!this.data.hasPreviousPage) {
            return ;
        }

        this.getCities(pageNum - 1);
    },

    // 下一页方法
    nextPage: function() {
        let pageNum = this.data.pageNum;

        if (!this.data.hasNextPage) {
            return ;
        }
        
        this.getCities(pageNum + 1);
    }

})