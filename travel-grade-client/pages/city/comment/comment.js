// pages/city/comment/comment.js
const user = require('../../../utils/user.js');
const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');
const app = getApp();

Page({

    /**
     * 页面的初始数据
     */
    data: {
        clear: '',
        scrollHeight: 0,
        cityId: '',
        list: [],
        userId: '',
        // 评论id
        id: '',
        // 用于记录最近的页码
        currentPage: 0,

        reply: false,
        replyUserName: '',
        toUserId: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        let cityId = options.id;
        let that = this;

        // 获取系统信息，设置滚动高度
        wx.getSystemInfo({
            success: function(res) {
                that.setData({
                    cityId: cityId,
                    scrollHeight: res.windowHeight
                })
            },
        })

    },

    onShow: function() {
        this.setData({
            list: []
        })
        // 开始获取城市评论
        this.getComments(0);
    },

    // 点击用户头像进入用户信息界面
    showUser: function(e) {
        let id = e.currentTarget.dataset.id;
        // 如果没有登录，先进行登录
        if (!app.globalData.hasLogin) {
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
        } else {
            // 如果不是自己
            console.log(id);
            console.log(this.data.userId);
            if (id != this.data.userId) {
                wx.navigateTo({
                    url: '/pages/user/other/other?userId=' + id,
                })
            } else {
                wx.switchTab({
                    url: '/pages/user/index/index',
                })
            }
        }
    },

    // 分页获取评论方法
    getComments: function(index) {
        let cityId = this.data.cityId;
        let that = this;
        let list = this.data.list;

        if (index == 0) {
            index = 1;
        }
        util.request(api.AllComment + '/' + cityId + '/' + index).then((res) => {
            // 判断是否还有评论
            if (res.data.list.length <= 0) {
                wx.showToast({
                    title: '没有更多了......',
                    duration: 1500,
                    icon: 'none'
                })
            }
            // 拼接评论
            let list_new = list.concat(res.data.list);
            // 进行数据绑定
            that.setData({
                list: list_new,
                userId: res.data.userId,
                currentPage: index
            })
        })
    },

    // 发送评论方法
    submitForm: function(e) {
        let that = this;

        if (app.globalData.hasLogin) {
            let content = e.detail.value.content;
            let toUserId = '';
            let id = '';
            if (content == '') {
                wx.showToast({
                    mask: true,
                    icon: 'none',
                    title: '请输入评论',
                })
                return;
            }

            // 如果是回复评论
            if (this.data.reply) {
                toUserId = this.data.toUserId;
                id = this.data.id;
            }

            // -----发送评论开始-----
            let data = {
                cityId: that.data.cityId,
                content: content,
                toUserId: toUserId,
                id: id,
                sendDate: util.formatTime(new Date)
            }
            console.log(data);
            util.request(api.SendComment, data, 'POST').then((res) => {
                console.log(res);
                let list = that.data.list;
                list.push(res.data.comment);
                that.setData({
                    list: list
                })
                wx.showToast({
                    title: '发送成功',
                    icon: 'success',
                    duration: 1500
                })
            })
            // -----发送评论结束-----

        } else {
            // util.showErrorToast("请先登录");
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
        }
        that.setData({
            clear: ''
        })
    },

    // 滑到底部事件
    bindDownLoad: function() {
        let currentPage = this.data.currentPage;
        this.getComments(currentPage + 1);
    },

    // 滑到顶部事件
    refresh: function() {
        this.setData({
            list: []
        })
        this.getComments(0);
    },

    // 删除评论方法
    deleteComment: function(e) {
        let id = e.currentTarget.dataset.commentid;
        let index = e.currentTarget.dataset.index;
        let list = this.data.list;
        let that = this;

        wx.showModal({
            title: '提示',
            content: '确定删除该评论？',
            success(res) {
                // 开始删除评论
                if (res.confirm) {
                    util.request(api.DeleteComment + '/' + id).then((res) => {
                        if (res.status == 200) {
                            list.splice(index, 1); // index表示起始位置，1表示删除几个元素（0表示不删除元素）
                            that.setData({
                                list: list
                            })
                        }
                    })
                } else if (res.cancel) {
                    return;
                }
            }

        })
    },

    // 点击回复按钮方法
    bindReply: function(e) {
        if (app.globalData.hasLogin) {
            console.log(e);
            this.setData({
                reply: true,
                replyUserName: e.currentTarget.dataset.commentusername,
                toUserId: e.currentTarget.dataset.touserid,
                id: e.currentTarget.dataset.commentid
            })
            console.log(this.data.id);
        } else {
            // util.showErrorToast("请先登录");
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
        }
    },

    // 查看更多评论方法
    moreBtn: function(e) {
        if (app.globalData.hasLogin) {
            let that = this;
            let commentId = e.currentTarget.dataset.commentid;
            let index = e.currentTarget.dataset.index + 1;
            let cityId = this.data.cityId;
            let list = this.data.list;

            let data = {
                commentId: commentId,
                cityId: cityId
            }
            console.log(data);

            util.request(api.MoreComment, data, 'POST').then((res) => {
                let list_more = res.data;
                if (list_more == null || list_more.length < 1) {
                    wx.showToast({
                        title: '暂时没有评论',
                        duration: 1000,
                        icon: 'none'
                    })
                    return ;
                }
                for (let i = 0; i < list_more.length; i++, index++) {
                    list.splice(index, 0, list_more[i]);
                }
                that.setData({
                    list: list
                })
            })
        } else {
            // util.showErrorToast("请先登录");
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
        }
    },

    // 取消回复评论方法
    cancleReply: function() {
        this.setData({
            reply: false
        })
    }
})