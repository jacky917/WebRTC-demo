package com.demo.webrtc.controller;


import com.demo.webrtc.constant.Constants;
import com.demo.webrtc.domain.vo.Result;
import com.demo.webrtc.domain.vo.sysmgr.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping(value = "/pub")
public class PubController {

    @ResponseBody
    @RequestMapping(value = "/login" ,method = {RequestMethod.POST})
    public Result<String> login(HttpServletResponse response, @Validated UserVo userVo){

        System.out.println(userVo);
        System.out.println("====================================");
        System.out.println("Username = " + userVo.getUsername());
        System.out.println("Password = " + userVo.getPassword());
        System.out.println("====================================");
        Subject currentUser = SecurityUtils.getSubject();

        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(userVo.getUsername(),userVo.getPassword());
            token.setRememberMe(Boolean.parseBoolean(userVo.getRemember()));
            try{
                currentUser.login(token);
            }catch (AuthenticationException ae){
                log.info("登入失敗 AuthenticationException " + ae.getMessage());
                return new Result<>(false, "wrong account or password", null, Constants.PARAMETERS_MISSING);
            }catch (Exception e){
                log.info("登入失敗 Exception " + e.getMessage());
                return new Result<>(false, "login failed", null, Constants.SERVER_ERROR);
            }
        }
        return new Result<>(true, "login success", userVo.getUsername(), Constants.TOKEN_CHECK_SUCCESS);

    }
}
