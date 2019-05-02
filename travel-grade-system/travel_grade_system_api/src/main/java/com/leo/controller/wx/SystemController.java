package com.leo.controller.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.leo.annotation.LoginUser;
import com.leo.manager.CaptchaManager;
import com.leo.pojo.TravelLog;
import com.leo.pojo.TravelUser;
import com.leo.vo.UserInfo;
import com.leo.vo.UserToken;
import com.leo.vo.WxUserInfo;
import com.leo.manager.TokenManager;
import com.leo.service.ILogService;
import com.leo.service.IUserService;
import com.leo.utils.*;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/system")
public class SystemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);
//    记录用户登录状态
    private static final Map<String, Boolean> loginMap = new HashMap<>();

    @Autowired
    private WxMaService wxService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILogService logService;

    /**
     * 用户使用微信登录接口
     * @param wxUserInfo
     * @param request
     * @return
     */
    @PostMapping("/login_by_wx")
    public MyResult loginByWx(@RequestBody WxUserInfo wxUserInfo, HttpServletRequest request) {
        LOGGER.info("------调用微信登录方法开始------");
        LOGGER.info("请求参数为：" + wxUserInfo);

//        获取微信登录码和微信用户信息
        String code = wxUserInfo.getCode();
        UserInfo userInfo = wxUserInfo.getUserInfo();

//        参数判空
        if (code == null || userInfo == null) {
            LOGGER.error("传递参数为空");
            return MyResult.errorMsg("用户信息为空");
        }

//        通过第三方类获取sessionKey以及openId
        String sessionKey = null;
        String openId = null;
        try {
//            sessionKey和openId的获取需要微信登录码
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (WxErrorException e) {
            e.printStackTrace();
            return MyResult.errorMsg("获取openid失败");
        }

        if (sessionKey == null || openId == null) {
            LOGGER.error("获取openId或sessionKey失败");
            return MyResult.errorMsg("服务器获取数据失败，请重试...");
        }

//        通过openId获得用户
        TravelUser user = userService.getUserByOpenId(openId);

        if (user == null) {
//            如果是第一次使用微信登录，创建新用户并保存相关信息
            user = new TravelUser();

            user.setUsername(openId);
            user.setPassword(openId);
            user.setOpenId(openId);
            user.setNickname(userInfo.getNickName());
            user.setAvatar(userInfo.getAvatarUrl());
            user.setGender(userInfo.getGender());
            user.setLevel(0);
            user.setAddTime(new Date());
            user.setUpdateTime(new Date());

//            添加用户并保存结果
            int result = userService.addUser(user);
//            判断添加用户结果
            if (result == 0) {
                LOGGER.error("添加用户失败");
                return MyResult.errorMsg("微信登录失败");
            }
            LOGGER.info("添加用户成功");
        }

//        判断用户是否有效
        if (!user.getDeleted()) {
            return MyResult.errorMsg("该用户已失效");
        }

//        添加用户令牌
        UserToken userToken = TokenManager.generateToken(user.getId());
        userToken.setSessionKey(sessionKey);

//        判断用户登录状态
        if (loginMap.get(user.getId()) == null || !loginMap.get(user.getId())) {
//            添加用户日志
            TravelLog  travelLog = new TravelLog();
            travelLog.setUserId(user.getId());
            travelLog.setLoginCity(userInfo.getCity());
            travelLog.setLoginProvince(userInfo.getProvince());
            travelLog.setLoginCountry(userInfo.getCountry());
            travelLog.setLoginIp(IpUtil.getIp(request));
            travelLog.setLoginTime(new Date());

            logService.addLog(travelLog);
            LOGGER.info("添加用户日志对象");

            loginMap.put(user.getId(), true);
            LOGGER.info("目前登录数：" + loginMap.size() + "，当前用户状态" + loginMap.get(user.getId()));
        }

//        设置返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", userToken.getToken());
        data.put("expireTime", userToken.getExpireTime().toString());
        data.put("userInfo", userInfo);

        return MyResult.ok(data);
    }

    /**
     * 用户登录接口
     * @param travelUser
     * @param request
     * @return
     */
    @PostMapping("/login")
    public MyResult login(@RequestBody TravelUser travelUser, HttpServletRequest request) {
        LOGGER.info("调用用户登录接口");
        LOGGER.info("请求参数为：" + travelUser);

        String username = travelUser.getUsername();
        String password = travelUser.getPassword();

        if (username == null || password == null) {
            LOGGER.error("用户名或密码为空");
            return MyResult.errorMsg("参数为空");
        }

//        查询用户
        List<TravelUser> userList = userService.getUsersByUsername(username);
        TravelUser user = null;
        if (userList.size() > 1) {
            LOGGER.error("数据中具有相同的用户名");
            return MyResult.errorMsg("系统内部错误");
        } else if (userList.size() == 0) {
            return MyResult.errorMsg("用户名错误");
        } else {
            user = userList.get(0);
        }

//        校验密码
        if (!user.getPassword().equals(password)) {
            return MyResult.errorMsg("密码不正确");
        }

//        判断用户是否有效
        if (!user.getDeleted()) {
            return MyResult.errorMsg("该用户已失效");
        }

//        判断用户是否正在登录
        Boolean logining = loginMap.get(user.getId());
        if (logining == null || !logining) {
//            添加日志
            TravelLog travelLog = new TravelLog();
            travelLog.setUserId(user.getId());
            travelLog.setLoginTime(new Date());
            travelLog.setLoginIp(IpUtil.getIp(request));
            logService.addLog(travelLog);
            LOGGER.info("添加用户日志对象");
//            将该登录日志的用户登录状态设为true
            loginMap.put(user.getId(), true);
            LOGGER.info("目前登录数：" + loginMap.size() + "，当前用户状态" + loginMap.get(user.getId()));
        }

//        设置微信用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

//        设置token
        UserToken userToken = TokenManager.generateToken(user.getId());

//        返回相关数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", userToken.getToken());
        data.put("userInfo", userInfo);
        data.put("expireTime", userToken.getExpireTime().toString());

        return MyResult.ok(data);
    }

    /**
     * 获取验证码接口
     */
    @PostMapping("/captcha")
    public MyResult getCaptcha(@RequestBody String body) {

        LOGGER.info("调用获取验证码接口");

//        获得手机号
        String mobile = JacksonUtil.parseString(body, "mobile");

//        检验手机号内容
        if (StringUtils.isEmpty(mobile)) {
            return MyResult.errorMsg("手机号为空");
        }

//        检验手机号格式
        if (!RegexUtil.isMobileExact(mobile)) {
            return MyResult.errorMsg("手机号格式错误");
        }

//        生成验证码
        String code = CharUtil.generateRandomNum(6);

//        添加手机号和验证码到缓存
        boolean success = CaptchaManager.addToCache(mobile, code);
//        添加失败情况
        if (!success) {
          return MyResult.errorMsg("验证码未超过1分钟,不能发送");
        }

//        返回相关数据
        Map<String, Object> data = new HashMap<>();
        data.put("code", code);

        return MyResult.ok(data);
    }

    /**
     * 用户注册接口
     * @param body
     * @param request
     * @return
     */
    @PostMapping("/register")
    public MyResult register(@RequestBody String body, HttpServletRequest request) throws Exception {

        LOGGER.info("调用用户注册接口");

//        获得请求体中参数值
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");
        String wxCode = JacksonUtil.parseString(body, "wxCode");

//        检查参数
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(wxCode) || StringUtils.isEmpty(code)) {
            return MyResult.errorMsg("注册信息不能为空");
        }

//        通过用户名查询用户列表
        List<TravelUser> userList = userService.getUsersByUsername(username);
        if (userList.size() > 0) {
            return MyResult.errorMsg("该用户名已注册");
        }

//        通过手机号查询用户列表
        userList = userService.getUsersByMobile(mobile);
        if (userList.size() > 0) {
            return MyResult.errorMsg("该手机号已注册");
        }

//        检验手机格式
        if (!RegexUtil.isMobileExact(mobile)) {
            return MyResult.errorMsg("手机号格式不正确");
        }

//        判断验证码
        String cacheCode = CaptchaManager.getCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
            return MyResult.errorMsg("验证码错误");
        }

        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(wxCode);
            openId = result.getOpenid();
        } catch (WxErrorException e) {
            e.printStackTrace();
            return MyResult.errorMsg("获取openid失败");
        }

//        通过openId查询用户列表
        userList = userService.getUsersByOpenId(openId);
        if (userList.size() > 1) {
            return MyResult.errorMsg("服务器错误");
        }

        if (userList.size() == 1) {
            TravelUser checkUser = userList.get(0);
            String checkUsername = checkUser.getUsername();
            String checkPassword = checkUser.getPassword();
            if (!checkUsername.equals(openId) || !checkPassword.equals(openId)) {
                return MyResult.errorMsg("openid已绑定账号");
            }
        }

//        设置用户属性
        TravelUser user = new TravelUser();
        user.setUsername(username);
        user.setNickname(username);
        user.setPassword(MD5Utils.getMD5Str(password));
        user.setMobile(mobile);
        user.setOpenId(openId);
        user.setLevel(0);
        user.setAddTime(new Date());
        user.setGender((byte)0);
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");

//        添加用户
        userService.addUser(user);

//        获取token
        UserToken userToken = TokenManager.generateToken(user.getId());

//        设置微信用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());
        userInfo.setGender(user.getGender());

//        返回相关数据
        Map<String, Object> data = new HashMap<>();
        data.put("token", userToken.getToken());
        data.put("tokenExpire", userToken.getExpireTime().toString());
        data.put("userInfo", userInfo);

        return MyResult.ok(data);
    }

    /**
     * 用户注销接口
     */
    @PostMapping("/loginout")
    public MyResult loginOut(@LoginUser String userId) {

        LOGGER.info("调用用户注销接口");

//        判空
        if (userId == null) {
            return MyResult.errorMsg("目前没有用户登录");
        }

//        移除用户id对应的token
        TokenManager.removeToken(userId);

//        判断用户登录状态
        if (loginMap.get(userId) != null && loginMap.get(userId)) {

//            如果正在登录，将状态设为下线
            loginMap.remove(userId);
            LOGGER.info("目前登录数：" + loginMap.size());

//            更新日志
            List<TravelLog> list  = logService.getLogsByUserId(userId);
            TravelLog travelLog = list.get(0);
            travelLog.setLogoutTime(new Date());
            logService.updateLog(travelLog);
        }

        return MyResult.ok();
    }
}
