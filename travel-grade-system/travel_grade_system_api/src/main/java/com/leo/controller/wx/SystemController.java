package com.leo.controller.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.leo.pojo.TravelLog;
import com.leo.pojo.TravelUser;
import com.leo.pojo.dao.UserInfo;
import com.leo.pojo.dao.UserToken;
import com.leo.pojo.dao.WxUserInfo;
import com.leo.pojo.util.TokenManager;
import com.leo.service.ILogService;
import com.leo.service.IUserService;
import com.leo.utils.IpUtil;
import com.leo.utils.MyResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login_by_wx")
    public MyResult loginByWx(@RequestBody WxUserInfo wxUserInfo, HttpServletRequest request) {
        LOGGER.info("调用微信登录方法");
        LOGGER.info("请求参数为：" + wxUserInfo);

        String code = wxUserInfo.getCode();
        UserInfo userInfo = wxUserInfo.getUserInfo();
        if (code == null || userInfo == null) {
            LOGGER.error("传递参数为空");
            return MyResult.errorMsg("用户信息为空");
        }

        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        if (sessionKey == null || openId == null) {
            LOGGER.error("获取openId或sessionKey失败");
            return MyResult.errorMsg("服务器获取数据失败，请重试...");
        }

//        添加用户
        TravelUser user = userService.getUserByOpenId(openId);
        int result = 0;
        if (user == null) {
            user.setUsername(openId);
            user.setPassword(openId);
            user.setNickname(userInfo.getNickName());
            user.setAvatar(userInfo.getAvatarUrl());
            user.setGender(userInfo.getGender());
            user.setLevel(0);
            user.setAddTime(new Date());
            user.setUpdataTime(new Date());

            result = userService.addUser(user);
        }
        if (result == 0) {
            LOGGER.error("添加用户失败");
            return MyResult.errorMsg("微信登录失败");
        }

//        添加用户令牌
        UserToken userToken = TokenManager.generateToken(user.getId());
        userToken.setSessionKey(sessionKey);

//        添加用户日志
        TravelLog  travelLog = logService.getLogByUserId(user.getId());
        travelLog.setUserId(user.getId());
        travelLog.setLoginCity(userInfo.getCity());
        travelLog.setLoginProvince(userInfo.getProvince());
        travelLog.setLoginProvince(userInfo.getCountry());
        travelLog.setLoginIp(IpUtil.getIp(request));
        travelLog.setLoginTime(new Date());
        logService.addLog(travelLog);

        Map<String, Object> data = new HashMap<>();
        data.put("token", userToken.getToken());
        data.put("expireTime", userToken.getExpireTime().toString());
        data.put("userInfo", userInfo);

        return MyResult.ok(data);
    }

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
}
