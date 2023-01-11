package com.demo.webrtc.controller;


import com.demo.webrtc.constant.Constants;
import com.demo.webrtc.domain.entity.WrUser;
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

        log.info(String.valueOf(userVo));
        log.info("====================================");
        log.info("Username = " + userVo.getUsername());
        log.info("Password = " + userVo.getPassword());
        log.info("====================================");
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

    @ResponseBody
    @RequestMapping(value = "/logout" ,method = {RequestMethod.GET,RequestMethod.POST})
    public Result<String> logout(HttpServletResponse response){
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated()){
            StringBuilder role = new StringBuilder();
            for(Object o : currentUser.getPrincipals()){
                WrUser s = (WrUser) o;
                role.append(s.getAccount()).append(",");
            }
            log.info("================登出================");
            log.info("Username = " + role);
            log.info("===================================");
            currentUser.logout();
            return new Result<>(true, "logout success", role.toString(), Constants.TOKEN_CHECK_SUCCESS);
        }
        return new Result<>(false,"logout failed", "", Constants.PARAMETERS_MISSING);
    }
}
