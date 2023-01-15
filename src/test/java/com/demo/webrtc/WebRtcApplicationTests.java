package com.demo.webrtc;

import com.demo.webrtc.bean.RolePool;
import com.demo.webrtc.domain.entity.WrRole;
import com.demo.webrtc.service.AccountService;
import com.demo.webrtc.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        log.info("=====================================");
        log.info(accountService.findUserByUserID("1").getAccount());
        log.info("-------------------------");
        log.info(accountService.findUserByUserName("admin").getAccount());
        log.info("-------------------------");
        log.info(accountService.findRolesByUserID("1").toString());
        log.info("-------------------------");
        log.info(accountService.findRolesByUserName("admin").toString());
        log.info("-------------------------");
        log.info(accountService.getRoleByID("1").getName());
        log.info(accountService.getRoleByID("2").getName());
        log.info(accountService.getRoleByID("3").getName());
        log.info("=====================================");
    }

    @Autowired
    RolePool rolePool;

    @Test
    void RoleConfigTest(){
        HashMap<String, WrRole> hashMap = rolePool.getRoles();
        for(String key : hashMap.keySet()){
            log.info(key);
        }
    }

    @Autowired
    RoomService roomService;

    @Test
    void roomTest(){
        System.out.println(roomService.getCurrentOnlineUsers());
    }

}
