package com.demo.webrtc.domain.entity;

import javax.websocket.Session;

public class Connection {

    /**
     * 客戶端IP
     */
    private String ip;

    /**
     * 用戶對象
     */
    private WrUser user;

    /**
     * 連接會話
     */
    private Session session;

}
