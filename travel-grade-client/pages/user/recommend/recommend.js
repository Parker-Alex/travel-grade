// pages/user/recommend/recommend.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

    data: {
        region: ['广东省', '广州市'],
        customItem: '全部',
        content: '',
        files: [],
        picUrls: [],
        hasPic: false,
        maxSize: 1
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

    // 选择需要上传的图片
    chooseImage: function(e) {
        if (this.data.files.length > this.data.maxSize) {
            util.showErrorToast('只能上传一张图片')
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
                    files: that.data.files.concat(res.tempFilePaths),
                    hasPic: true
                })
            },
        })
    },

    // 提交事件
    doPost: function(e) {
        let that = this;
        let files = this.data.files;
        let content = this.data.content;
        let region = this.data.region;
        let token = wx.getStorageSync('token');
        console.log(token);

        if (content == null || content.length <= 0) {
            util.showErrorToast('推荐理由不能为空')
            return false;
        }
        if (region == null || content.length <= 0) {
            util.showErrorToast('请选择推荐城市')
            return false;
        }
        if (!this.data.hasPic) {
            util.showErrorToast('请选择图片')
            return false;
        }

        wx.uploadFile({
            url: api.AddRecommendCity,
            formData: {
                content: content,
                provinceName: region[0],
                cityName: region[1],
                token: token
            },
            filePath: files[0],
            name: 'file',
            header: {
                'Content-Type': 'application/json',
                'X-Leo-Token': wx.getStorageSync('token')
            },
            success(res) {
                console.log(res);
            }
        })       
    },

    // 上传图片方法
    upload: function(res) {
        console.log(res.tempFilePaths[0]);
        let that = this;
        let uploadTask = wx.uploadFile({
            url: api.UploadImage,
            filePath: res.tempFilePaths[0],
            name: 'file',
            success(res) {
                let _res = JSON.parse(res.data);
                console.log(_res);
            },
            fail: function (e) {
                wx.showModal({
                    title: '错误',
                    content: '上传失败',
                    showCancel: false
                })
            }
        })
        uploadTask.onProgressUpdate((res) => {
            console.log('上传进度', res.progress)
            console.log('已经上传的数据长度', res.totalBytesSent)
            console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend)
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