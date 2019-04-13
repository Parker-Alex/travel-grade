// 定义服务器API地址
var WxApiRoot = 'http://localhost:8888/wx/';

module.exports = {

    /**
     * 用户相关操作API
     */
    LoginByWx: WxApiRoot + 'system/login_by_wx',//微信登录
    LoginByAccount: WxApiRoot + 'system/login',//账号登录
    Register: WxApiRoot + 'system/register',//账号注册
    LoginOut: WxApiRoot + 'system/loginout',//注销
    RegisterCaptcha: WxApiRoot + 'system/captcha',//注册验证码
    UpdateUserCityRel: WxApiRoot + 'user/update_user_city_rel',//更新用户与城市之间关系

    /**
     * 首页相关操作API
     */
    IndexData: WxApiRoot + 'index/index_data',//获取城市和省份列表

    GetProvinces: WxApiRoot + 'index/get_provinces',//获取省份列表
    GetCitiesByProId: WxApiRoot + 'index/get_cities',//通过省份id获得城市列表

    GetHotKeyAndName: WxApiRoot + 'search/hot_key',//得到搜索的热门词和所有城市名称
    GetCity: WxApiRoot + 'search/search',//得到城市信息

    /**
     * 评论相关API
     */
    SendComment: WxApiRoot + 'comment/send',//发表评论
    AllComment: WxApiRoot + 'comment/all',//获得所有评论
    DeleteComment: WxApiRoot + 'comment/delete',//删除评论
    MoreComment: WxApiRoot + 'comment/more',//更多评论

    /**
     * 城市相关操作
     */
    CityList: WxApiRoot + 'city/city_list',//根据不同条件获得城市列表
    
}