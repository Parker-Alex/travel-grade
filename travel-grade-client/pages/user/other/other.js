// pages/user/other/other.js
const app = getApp();
const util = require('../../../utils/util.js');
const user = require('../../../utils/user.js');
const api = require('../../../config/api.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        isMe: true,
        isFollow: false,
        user: {},
        fans: [],
        comments: [],
        recommends: [],
        like_cities: [],
        gone_cities: [],
        cities: [],

        isSelectdGone: "video-info-selected",
        isSelectdLike: "",
        isSelectdRecommend: "",
        //列表展示
        myGoneFalg: false,
        myLikeFalg: true,
        myRecommendFalg: true
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        // let userId = options.userId;
        // this.getOtherUser(userId);

        this.getOtherUser('190221865XWCADWH');
    },

    // 得到用户信息方法
    getOtherUser: function(id) {
        let that =  this;
        util.request(api.GetOtherUser + '/' + id).then((res) => {
            console.log(res);
            that.setData({
                user: res.data.user,
                fans: res.data.fans,
                comments: res.data.comments,
                recommends: res.data.recommends,
                like_cities: res.data.like_cities,
                gone_cities: res.data.gone_cities
            })
        })
    },

    // 查看去过城市列表
    doSelectGone: function(e) {
        console.log(this.data.gone_cities);
        this.setData({
            myGoneFalg: false,
            myLikeFalg: true,
            myRecommendFalg: true,
            isSelectdGone: "video-info-selected",
            isSelectdLike: "",
            isSelectdRecommend: "",
            cities: this.data.gone_cities
        })
    },

    // 查看想去城市列表
    doSelectLike: function(e) {
        console.log(this.data.like_cities);
        this.setData({
            myGoneFalg: true,
            myLikeFalg: false,
            myRecommendFalg: true,
            isSelectdGone: "",
            isSelectdLike: "video-info-selected",
            isSelectdRecommend: "",
            cities: this.data.like_cities
        })
    },

    // 查看推荐城市列表
    doSelectdRecommend: function(e) {
        this.setData({
            myGoneFalg: true,
            myLikeFalg: true,
            myRecommendFalg: false,
            isSelectdGone: "",
            isSelectdLike: "",
            isSelectdRecommend: "video-info-selected"
        })
    },

    // onShow: function(e) {
    //     let that = this;
    //     if (this.data.myGoneFalg) {
    //         this.setData({
    //             cities: that.data.gone_cities
    //         })
    //     }
    //     if (this.data.myLikeFalg) {
    //         this.setData({
    //             cities: that.data.like_cities
    //         })
    //     }
    // },

    // 更换头像
    changeFace: function () {
        var that = this;
        wx.chooseImage({
            count: 1, //上传数量
            sizeType: ['compressed'], //进行压缩
            sourceType: ['album', 'camera'], //从相册或相机获取图片
            success: function (res) {
                //tempFilePaths放在image中src属性中，用于显示图片
                let tempFilePaths = res.tempFilePaths;
                let url = app.serverUrl;
                // let user = app.userInfo;
                let user = app.getGlobalUserInfo();
                wx.showLoading({
                    title: '请稍等...',
                    mask: true
                });
                wx.uploadFile({
                    url: url + '/user/uploadFaceImg/' + user.id,
                    filePath: tempFilePaths[0],
                    name: 'file',
                    header: {
                        "content-type": "application/json",
                        // "userId": user.id,
                        // "token": user.userToken
                    },
                    success(res) {
                        wx.hideLoading();
                        // 上传成功后返回的是字符串，所以需要解析成JSON对象
                        let data = JSON.parse(res.data);
                        if (data.status == 200) {
                            wx.showToast({
                                title: '上传成功',
                                icon: 'success',
                                duration: 1000
                            });
                            let imageUrl = data.data;
                            that.setData({
                                faceUrl: url + imageUrl
                            });
                        } else {
                            wx.showToast({
                                title: data.msg,
                                icon: 'none',
                                duration: 1000
                            });
                        }
                    },
                    fail() {
                        wx.hideLoading();
                        wx.showToast({
                            title: '连接超时',
                            image: '/images/error.png'
                        });
                    },
                    complete(res) {
                        //无论成功与否都要执行这个方法
                        wx.hideLoading();
                    }
                });
            },
        });
    },

    // 选择视频
    uploadVideo: function () {
        wx.chooseVideo({
            sourceType: ['album', 'camera'],
            compressed: true,//视频经过压缩，默认为true
            maxDuration: 10,//最大时长
            success(res) {
                // 视频时长
                let duration = res.duration;
                // 视频高度
                let tmpHeight = res.height;
                // 视频宽度
                let tmpWidth = res.width;
                // 视频临时路径
                let tmpVideoUrl = res.tempFilePath;
                // 视频临时首页
                let tmpCoverUrl = res.thumbTempFilePath;
                if (duration > 10) {
                    wx.showToast({
                        title: '视频时长不能超过10秒',
                        icon: 'none',
                        duration: 1000
                    });
                } else if (duration < 1) {
                    wx.showToast({
                        title: '视频时长不能小于1秒',
                        icon: 'none',
                        duration: 1000
                    });
                } else {
                    // 跳转到选择bgm页面
                    wx.navigateTo({
                        url: '/pages/bgm/bgm?duration=' + duration
                            + '&tmpHeight=' + tmpHeight + '&tmpWidth=' + tmpWidth
                            + '&tmpVideoUrl=' + tmpVideoUrl + '&tmpCoverUrl=' + tmpCoverUrl
                    });
                }
            }
        });
    }
})