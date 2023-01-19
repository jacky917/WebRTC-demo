package com.demo.webrtc.controller;

import com.demo.webrtc.config.shiro.ShiroKit;
import com.demo.webrtc.constant.Constants;
import com.demo.webrtc.domain.entity.WrUser;
import com.demo.webrtc.domain.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class Test {


    @RequestMapping(value = "/api/pub/whoami", method = {RequestMethod.POST, RequestMethod.GET})
    public Result<String> pubRedirect() {
        WrUser user = ShiroKit.getCurrentUser();
        System.out.println((user != null) ? user.getAccount() : null);
        return new Result<>(true, "whoami", (user != null) ? user.getAccount() : null, Constants.TOKEN_CHECK_SUCCESS);
    }

//    @RequestMapping(value = "/websocket", method = {RequestMethod.POST, RequestMethod.GET})
//    public String websocketTest() {
//        return "websocket";
//    }


//    @RequestMapping(value = "/pub/{url}", method = {RequestMethod.POST, RequestMethod.GET})
//    public String pubRedirect(@PathVariable String url) {
//        StringBuilder role = new StringBuilder();
//        Subject subject = SecurityUtils.getSubject();
//        if (subject.isAuthenticated()) {
//            for (Object o : subject.getPrincipals()) {
//                WrUser s = (WrUser) o;
//                role.append(s.getAccount()).append(",");
//            }
//        }
//        return "redirect : " + url + "\nroles : " + role;
//    }

    @RequestMapping(value = "/sysmgr/{url}", method = {RequestMethod.POST, RequestMethod.GET})
    public String sysRedirect(@PathVariable String url) {
        return "redirect : " + url;
    }

    @RequestMapping(value = "/api/pub/loginTest", method = {RequestMethod.POST, RequestMethod.GET})
    public Result<String> login(@RequestParam("username") String username,
                                @RequestParam("password") String password) {

        if (username == null || password == null)
            return new Result<>(false, "login failed", username, Constants.PARAMETERS_MISSING);
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (AuthenticationException ae) {
                log.info("登入失敗 AuthenticationException " + ae.getMessage());
                return new Result<>(false, "login failed", username, Constants.PARAMETERS_MISSING);
            } catch (Exception e) {
                log.info("登入失敗 Exception " + e.getMessage());
                return new Result<>(false, "login failed", username, Constants.SERVER_ERROR);
            }
        }
        return new Result<>(true, "login success", username, Constants.TOKEN_CHECK_SUCCESS);
    }

    @RequestMapping(value = "/unauth", method = {RequestMethod.POST, RequestMethod.GET})
    public String unauth() {
        return "unauth";
    }

}
