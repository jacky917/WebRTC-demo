package com.demo.webrtc.domain.entity;

import lombok.Data;

@Data
public class Message {

    private String command;
    private String message;
    // 目標用戶暱稱
    private String userName;

    public Message(){

    }

    public Message(String command, String message){
        this.command = command;
        this.message = message;
    }

    public Message(String command, String message, String userName){
        this.command = command;
        this.message = message;
        this.userName = userName;
    }
}
