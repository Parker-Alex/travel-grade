package com.leo.annotation.support;

import com.leo.annotation.LoginUser;
import com.leo.manager.TokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义方法参数解析器，解析请求头中token
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String LOGIN_TOKEN_KEY = "X-Leo-Token";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
//        判断参数类型是否是指定类型String 并且 是否拥有注解LoginUser，如果符合，则该参数是可支持参数
        return methodParameter.getParameterType().isAssignableFrom(String.class)
                && methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                        NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

//        得到请求头中的token参数
        String token = nativeWebRequest.getHeader(LOGIN_TOKEN_KEY);
        if (token == null || token.isEmpty()) {
            return null;
        }

//        通过token得到用户id
        return TokenManager.getUserId(token);
    }
}
