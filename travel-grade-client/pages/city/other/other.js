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
        cityId: '',

        // 水果评分相关信息
        array0: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        index0: -1,
        myGrade0: 0,
        grade0: 0,
        starts0: [],

        // 交通评分相关信息
        array1: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        index1: -1,
        myGrade1: 0,
        grade1: 0,
        starts1: [],

        // 天气评分相关信息
        array2: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        index2: -1,
        myGrade2: 0,
        grade2: 0,
        starts2: [],

        // 美食评分相关信息
        array3: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        index3: -1,
        myGrade3: 0,
        grade3: 0,
        starts3: [],

        // 住宿评分相关信息
        array4: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
        index4: -1,
        myGrade4: 0,
        grade4: 0,
        starts4: [],

        
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let cityId = options.cityId;
        this.setData({
            cityId: cityId
        })
    },

    onShow: function () {
        this.getOtherGrade();
    },

    // 得到其他服务评分信息
    getOtherGrade: function() {
        let cityId = this.data.cityId;
        let that = this;

        util.request(api.GetOthersGrade + '/' + cityId).then((res) => {
            console.log(res);
            console.log(res.data.user_grade);
            if (res.data.user_grade != null && res.data.user_grade.length > 0) {
                console.log(111);
                console.log(res.data.user_grade[0]);
                that.setData({
                    myGrade0: res.data.user_grade[0].grade,
                    myGrade1: res.data.user_grade[1].grade,
                    myGrade2: res.data.user_grade[2].grade,
                    myGrade3: res.data.user_grade[3].grade,
                    myGrade4: res.data.user_grade[4].grade
                })
            }
            that.setData({
                starts0: util.converStars(res.data.other_grade[0]),
                starts1: util.converStars(res.data.other_grade[1]),
                starts2: util.converStars(res.data.other_grade[2]),
                starts3: util.converStars(res.data.other_grade[3]),
                starts4: util.converStars(res.data.other_grade[4]),
                grade0: res.data.other_grade[0],
                grade1: res.data.other_grade[1],
                grade2: res.data.other_grade[2],
                grade3: res.data.other_grade[3],
                grade4: res.data.other_grade[4],
            })
        })
    },

    bindPickerChange(e) {
        user.checkLogin().then(() => {
            let type = e.currentTarget.dataset.type;
            if (type == 0) {
                let index0 = e.detail.value;
                let myGrade0 = this.data.array0[index0];

                this.setData({
                    index0: index0,
                    myGrade0: myGrade0                    
                })
            } else if (type == 1) {
                let index1 = e.detail.value;
                let myGrade1 = this.data.array1[index1];

                this.setData({
                    index1: index1,
                    myGrade1: myGrade1
                })
            } else if (type == 2) {
                let index2 = e.detail.value;
                let myGrade2 = this.data.array2[index2];

                this.setData({
                    index2: index2,
                    myGrade2: myGrade2
                })
            } else if (type == 3) {
                let index3 = e.detail.value;
                let myGrade3 = this.data.array3[index3];

                this.setData({
                    index3: index3,
                    myGrade3: myGrade3
                })
            } else if (type == 4) {
                let index4 = e.detail.value;
                let myGrade4 = this.data.array4[index4];

                this.setData({
                    index4: index4,
                    myGrade4: myGrade4
                })
            }
        }).catch(() => {
            wx.showModal({
                title: '提示',
                content: '请先登录',
                success(res) {
                    if (res.confirm) {
                        wx.navigateTo({
                            url: '/pages/system/login/login',
                        })
                    } else if (res.cancel) {
                        return;
                    }
                }
            })
        })
    },

    postOtherGrade: function() {
        let cityId = this.data.cityId;

        let grade0 = this.data.array0[this.data.index0];
        let grade1 = this.data.array1[this.data.index1];
        let grade2 = this.data.array2[this.data.index2];
        let grade3 = this.data.array3[this.data.index3];
        let grade4 = this.data.array4[this.data.index4];

        // 如果用户进行了评分才提交
        if (grade0 > 0 || grade1 > 0 || grade2 > 0 || grade3 > 0 || grade4 > 0) {
            util.request(api.UpdateOtherGrade + '/' + cityId, {
                grade0: grade0,
                grade1: grade1,
                grade2: grade2,
                grade3: grade3,
                grade4: grade4
            }, 'POST').then((res) => {
                console.log(res);

            })
        } else {
            return ;
        }
    },

    onUnload: function (options) {
        console.log("onUnload");
        this.postOtherGrade();
    }
})