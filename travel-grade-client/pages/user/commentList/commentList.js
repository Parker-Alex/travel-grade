const app = getApp();
const util = require('../../../utils/util.js');
const user = require('../../../utils/user.js');
const api = require('../../../config/api.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        comments: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getComments();
    },

    // 得到用户评论
    getComments: function() {
        let that = this;
        util.request(api.UserComments).then((res) => {
            console.log(res.data);

            that.setData({
                comments: res.data
            })
        })
    },

    // 删除评论
    deleteComment: function(e) {
        let id = e.currentTarget.dataset.commentid;
        let that = this;

        wx.showModal({
            title: '提示',
            content: '确定删除该评论？',
            success(res) {
                // 开始删除评论
                if (res.confirm) {
                    util.request(api.DeleteComment + '/' + id).then((res) => {
                        if (res.status == 200) {
                            // index表示起始位置，1表示删除几个元素（0表示不删除元素）
                            // list.splice(index, 1); 
                            // that.setData({
                            //     list: list
                            // })
                            that.getComments();
                        }
                    })
                } else if (res.cancel) {
                    return;
                }
            }

        })
    }
})