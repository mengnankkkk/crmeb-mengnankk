package com.mengnankk.front.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mengnankk.common.response.CommonResult;
import com.mengnankk.common.token.FrontTokenComponent;
import com.mengnankk.common.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class FrontTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private FrontTokenComponent frontTokenComponent;
    //程序处理之前需要处理的业务
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object hander) throws Exception{
        response.setCharacterEncoding("UTF-8");
        String token = frontTokenComponent.getToken(request);
        if (token==null||token.isEmpty()){
           //判断路由，部分路由不管用户是否登录都可以访问
            boolean result = frontTokenComponent.checkRouter(RequestUtil.getUri(request));
            if (result){
                return true;
            }
            response.getWriter().write(JSONObject.toJSONString(CommonResult.unauthorized()));
            return false;
        }
        Boolean result = frontTokenComponent.check(token,request);
        if (!result){
            response.getWriter().write(JSONObject.toJSONString(CommonResult.unauthorized()));
            return false;
        }
        return true;
    }
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object hander, ModelAndView modelAndView){

    }
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
