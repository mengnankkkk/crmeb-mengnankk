package com.mengnankk.front.service;

import com.mengnankk.common.model.user.User;
import com.mengnankk.common.request.LoginMobileRequest;
import com.mengnankk.common.request.LoginRequest;
import com.mengnankk.common.response.LoginResponse;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    /**
     * 账号密码登录
     * @param loginRequest
     * @return
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 手机号登录
     * @param loginRequest
     * @return
     */
    LoginResponse phoneLogin(LoginMobileRequest loginRequest);

    /**
     * 老绑定分销关系
     * @param user
     * @param spreadUid
     * @return
     */
    Boolean bindSpread(User user,Integer spreadUid);

    /**
     * 退出登录
     * @param request
     */
    void loginOut(HttpServletRequest request);

}
