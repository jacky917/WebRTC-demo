package com.demo.webrtc.bean;


import com.demo.webrtc.config.ConfiguratorForClientIp;
import com.demo.webrtc.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket", configurator = ConfiguratorForClientIp.class)
@Component
@Slf4j
public class WebSocket {

    @Autowired
    AccountService accountService;

    /**
     * WebSocket連接建立成功
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info("用戶 {} 建立WebSocket連接成功",accountService.getCurrentUser().getAccount());
    }
    /**
     * WebSocket關閉連接
     */
    @OnClose
    public void onClose(Session session) {
        log.info("用戶 {} 關閉WebSocket連接成功",accountService.getCurrentUser().getAccount());
    }

    /**
     * WebSocket發生錯誤
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("用戶 {} WebSocket連接錯誤",accountService.getCurrentUser().getAccount());
        error.printStackTrace();
    }

    /**
     * WebSocket獲取到客戶端消息
     * @param stringMessage 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(Session session, String stringMessage) {

    }

}
