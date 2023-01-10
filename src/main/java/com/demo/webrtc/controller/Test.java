package com.demo.webrtc.controller;

import com.demo.webrtc.constant.Constants;
import com.demo.webrtc.domain.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Test {

    @RequestMapping(value = "/pub/{url}", method = {RequestMethod.POST, RequestMethod.GET})
    public String pubRedirect(@PathVariable String url) {
        return "redirect : " + url;
    }

    @RequestMapping(value = "/sysmgr/{url}", method = {RequestMethod.POST, RequestMethod.GET})
    public String sysRedirect(@PathVariable String url) {
        return "redirect : " + url;
    }

    @RequestMapping(value = "/pub/loginTest", method = {RequestMethod.POST, RequestMethod.GET})
    public Result<String> login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        if(username == null || password == null)
            return new Result<>(false, "login failed", username, Constants.PARAMETERS_MISSING);
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            token.setRememberMe(true);
            try{
                currentUser.login(token);
            }catch (AuthenticationException ae){
                System.out.println("登入失敗 AuthenticationException " + ae.getMessage());
                return new Result<>(false, "login failed", username, Constants.PARAMETERS_MISSING);
            }catch (Exception e){
                System.out.println("登入失敗 Exception " + e.getMessage());
                return new Result<>(false, "login failed", username, Constants.SERVER_ERROR);
            }
        }
        return new Result<>(true, "login success", username, Constants.TOKEN_CHECK_SUCCESS);
    }

    @RequestMapping(value = "/pub/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout() {
        return "logout";
    }


    @RequestMapping(value = "/unauth", method = {RequestMethod.POST, RequestMethod.GET})
    public String unauth() {
        return "unauth";
    }

}
