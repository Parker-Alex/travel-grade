// pages/user/recommend/recommend.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

    data: {
        region: ['广东省', '广州市'],
        customItem: '全部',
        content: '',
        files: []
    },
    bindRegionChange(e) {
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.setData({
            region: e.detail.value
        })
    },

    onLoad: function (options) {

    },

    // 输入推荐理由
    bindInputValue: function(e) {
        let content = e.detail.value;

        if (content && content.length > 140) {
            return false;
        }

        this.setData({
            content: content
        })
    },

    // 选择图片
    chooseImage: function(e) {
        if (this.data.files.length >= 5) {
            util.showErrorToast('只能上传五张图片')
            return false;
        }

        let that = this;
        wx.chooseImage({
            count: 1,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
            success: function(res) {
                console.log(res);
                that.setData({
                    files: that.data.files.concat(res.tempFilePaths)
                })
            },
        })
    },

    // 查看图片
    previewImage: function(e) {
        console.log(e);
        wx.previewImage({
            urls: this.data.files,// 需要预览的图片http地址列表
            current: e.currentTarget.id// 当前显示的图片http地址
        })
    }
})