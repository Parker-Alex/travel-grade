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
    UploadImage: WxApiRoot + 'user/upload_image',//上传推荐城市图片
    AddRecommendCity: WxApiRoot + 'user/add_recommend',//添加推荐城市
    GetOtherUser: WxApiRoot + 'user/get_other_user',//获取提起用户信息
    UserRecommendCities: WxApiRoot + 'user/recommend_cities',//获得用户推荐城市列表
    UpdateOtherGrade: WxApiRoot + 'user/update_other',//更新其他服务评分
    GetOthersGrade: WxApiRoot + 'user/others_grade',//得到其他服务评分信息
    UserGoneCities: WxApiRoot + 'user/gone_cities',//获得用户去过城市列表
    UserLikeCities: WxApiRoot + 'user/like_cities',//获得用户想去城市列表
    UserFavourCities: WxApiRoot + 'user/favour_cities',//获得用户点赞城市列表
    UserComments: WxApiRoot + 'user/comments',//获得用户评论列表
    UserFans: WxApiRoot + 'user/fans',//获得用户粉丝用户列表
    UserFollows: WxApiRoot + 'user/follows',//获得用户关注用户列表 
    Follow: WxApiRoot + 'user/follow',//关注或取消关注用户

    /**
     * 首页相关操作API
     */
    IndexData: WxApiRoot + 'index/index_data',//获取城市和省份列表

    GetProvinces: WxApiRoot + 'index/get_provinces',//获取省份列表
    GetCitiesByProId: WxApiRoot + 'index/get_cities',//通过省份id获得城市列表

    /**
     * 搜索相关操作API
     */
    GetHotKeyAndName: WxApiRoot + 'search/hot_key',//得到搜索的热门词和所有城市名称
    GetCity: WxApiRoot + 'search/city',//得到单个城市对象信息
    GetProvince: WxApiRoot + 'search/province',//得到单个省份对象信息

    /**
     * 评论相关API
     */
    SendComment: WxApiRoot + 'comment/send',//发表评论
    AllComment: WxApiRoot + 'comment/all',//获得所有评论
    DeleteComment: WxApiRoot + 'comment/delete',//删除评论
    MoreComment: WxApiRoot + 'comment/more',//更多评论

    /**
     * 城市和省份相关操作
     */
    CityList: WxApiRoot + 'city/city_list',//根据不同条件获得城市列表

}