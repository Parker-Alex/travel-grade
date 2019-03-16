const api = require('../../config/api.js');
const util = require('../../utils/util.js');
const WxSearch = require('../wxSearchView/wxSearchView.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        hotList: [],
        citiesName: []
    },

    onLoad: function(options) {
        let that = this;
        util.request(api.GetHotKeyAndName).then((res) => {
            console.log(res);
            let hotList = res.data.hotkeys;
            let citiesName = res.data.citiesName;
            WxSearch.init(
                that,//本页面的引用
                hotList,//热词列表，[]表示为空
                citiesName,//搜索匹配列表，[]表示为空
                that.mySearchFunction,//搜索回调函数
                that.myGobackFunction//返回回调函数
            )
        })
    },

    // 3 转发函数，固定部分，直接拷贝即可
    wxSearchInput: WxSearch.wxSearchInput,  // 输入变化时的操作
    wxSearchKeyTap: WxSearch.wxSearchKeyTap,  // 点击提示或者关键字、历史记录时的操作
    wxSearchDeleteAll: WxSearch.wxSearchDeleteAll, // 删除所有的历史记录
    wxSearchConfirm: WxSearch.wxSearchConfirm,  // 搜索函数
    wxSearchClear: WxSearch.wxSearchClear,  // 清空函数

    mySearchFunction: function (value) {
       wx.redirectTo({
           url: '/pages/detail/detail?value=' + value,
       })
    },

    myGobackFunction: function() {
        wx.navigateBack({
            delta: 1
        })
    }


})