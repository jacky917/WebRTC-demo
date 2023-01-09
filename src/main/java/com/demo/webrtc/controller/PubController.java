package com.demo.webrtc.controller;


import com.demo.webrtc.constant.Constants;
import com.demo.webrtc.domain.vo.Result;
import com.demo.webrtc.domain.vo.sysmgr.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/pub")
public class PubController {

    @ResponseBody
    @RequestMapping(value = "/login" ,method = {RequestMethod.POST})
    public Result<UserVo> login(HttpServletResponse response, UserVo userVo){
        System.out.println(userVo);
        return new Result<UserVo>(true, "login success", userVo, Constants.TOKEN_CHECK_SUCCESS);
    }
}
