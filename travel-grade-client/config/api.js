// 定义服务器API地址
var WxApiRoot = 'http://localhost:8888/wx/';

module.exports = {

    LoginByWx: WxApiRoot + 'system/login_by_wx',//微信登录
    LoginByAccount: WxApiRoot + 'system/login',//账号登录
}