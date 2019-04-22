// pages/city/other/other.js
const user = require('../../../utils/user.js');
const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const app = getApp();

Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 水果评分相关信息
        array0: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        index0: -1,
        myGrade0: 0,

        // 交通评分相关信息
        array1: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        index1: -1,
        myGrade1: 0
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },

    bindPickerChange(e) {
        // user.checkLogin().then(() => {
            let type = e.currentTarget.dataset.type;
            if (type == 0) {
                this.setData({
                    index0: e.detail.value
                })
            } else if (type == 1) {
                this.setData({
                    index1: e.detail.value
                })
            }
        // }).catch(() => {
        //     wx.showModal({
        //         title: '提示',
        //         content: '请先登录',
        //         success(res) {
        //             if (res.confirm) {
        //                 wx.navigateTo({
        //                     url: '/pages/system/login/login',
        //                 })
        //             } else if (res.cancel) {
        //                 return;
        //             }
        //         }
        //     })
        // })
    },

    postOtherGrade: function() {
        let grade0 = this.data.array0[this.data.index0];
        let grade1 = this.data.array1[this.data.index1];

        if (grade0 > 0 || grade1 > 0) {
            util.request(api.UpdateOtherGrade, {
                grade0: grade0,
                grade1: grade1
            }, 'POST').then((res) => {
                console.log(res);
            })
        }
    },

    onUnload: function (options) {
        console.log("onUnload");
        this.postOtherGrade();
    }
})