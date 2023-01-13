package com.demo.webrtc.bean;

import com.demo.webrtc.config.WebSocketConfig;
import com.demo.webrtc.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@ServerEndpoint(value = "/websocket", configurator = WebSocketConfig.class)
//@ServerEndpoint(value = "/websocket")
@Component
public class WebSocket {

    @Autowired
    AccountService accountService;

    /**
     * WebSocket連接建立成功
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
//        String ip = (String) session.getUserProperties().get("clientIp");
//        System.out.println(ip);
//        WrUser user = (WrUser) session.getUserProperties().get("user");
//        System.out.println(session.getUserProperties().get("user"));

//        WrUser userPrincipal = (WrUser) config.getUserProperties().get("UserPrincipal");
//        String user = null;
//        try {
//            user = (String) userPrincipal.getAccount();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        session.getUserProperties().put("user", user);
//        System.out.println("!!!!!!!! " + user);
        System.out.println("===============================================");
//        WrUser user = (WrUser)config.getUserProperties().get("UserPrincipal");
//        System.out.println(user.getAccount());
//        log.info("用戶 {} 建立WebSocket連接成功",user.getAccount());
    }

    /**
     * WebSocket關閉連接
     */
    @OnClose
    public void onClose(Session session) {
//        log.info("用戶 {} 關閉WebSocket連接成功",accountService.getCurrentUser().getAccount());
    }

    /**
     * WebSocket發生錯誤
     */
    @OnError
    public void onError(Session session, Throwable error) {
//        log.info("用戶 {} WebSocket連接錯誤",accountService.getCurrentUser().getAccount());
        error.printStackTrace();
    }

    /**
     * WebSocket獲取到客戶端消息
     *
     * @param stringMessage 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(Session session, String stringMessage) {
        System.out.println(stringMessage);
    }

}
