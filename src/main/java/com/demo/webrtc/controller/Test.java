package com.demo.webrtc.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Test {

    @RequestMapping(value = "/{url}", method = {RequestMethod.POST, RequestMethod.GET})
    public String redirect(@PathVariable String url) {
        return "redirect : " + url;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            token.setRememberMe(true);
            try{
                currentUser.login(token);
            }catch (AuthenticationException ae){
                System.out.println("登入失敗 AuthenticationException " + ae.getMessage());
            }catch (Exception e){
                System.out.println("登入失敗 Exception " + e.getMessage());
            }
        }
        return "login";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout() {
        return "logout";
    }


    @RequestMapping(value = "/unauth", method = {RequestMethod.POST, RequestMethod.GET})
    public String unauth() {
        return "unauth";
    }

}
