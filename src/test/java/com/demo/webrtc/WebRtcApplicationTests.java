package com.demo.webrtc;

import com.demo.webrtc.bean.RoleConfig;
import com.demo.webrtc.domain.entity.WrRole;
import com.demo.webrtc.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class WebRtcApplicationTests {

//    @Autowired
//    WrPostMapper wrPostMapper;
//
//    @Test
//    void contextLoads() {
//        WrPost wrPost = new WrPost();
//        wrPost.setId(1L);
//        wrPost.setYnFlag("1");
//        wrPostMapper.insert(wrPost);
//    }

    @Autowired
    AccountService accountService;



    @Test
    void AccountServiceTest(){
        System.out.println("=====================================");
        System.out.println(accountService.findUserByUserID("1").getAccount());
        System.out.println("-------------------------");
        System.out.println(accountService.findUserByUserName("admin").getAccount());
        System.out.println("-------------------------");
        System.out.println(accountService.findRolesByUserID("1"));
        System.out.println("-------------------------");
        System.out.println(accountService.findRolesByUserName("admin"));
        System.out.println("-------------------------");
        System.out.println(accountService.getRoleByID("1").getName());
        System.out.println(accountService.getRoleByID("2").getName());
        System.out.println(accountService.getRoleByID("3").getName());
        System.out.println("=====================================");
    }

    @Autowired
    RoleConfig roleConfig;

    @Test
    void RoleConfigTest(){
        HashMap<String, WrRole> hashMap = roleConfig.RoleName();
        for(String key : hashMap.keySet()){
            System.out.println(key);
        }
    }

}
