package com.demo.webrtc.service;

import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.domain.entity.Message;

public interface MessageService{

    /**
     * 給所有人發消息
     * @param command 指令
     * @param message 消息內容
     */
    void sendMessageToEveryOne(String command, String message);

    /**
     * 給除了自己以外的所有人發消息
     * @param command 指令
     * @param message 消息內容
     * @param connection 自身的連接信息
     */
    void sendMessageToEveryOneExcludeSelf(String command, String message, Connection connection);

    /**
     * 給特定用戶發消息
     * @param message 消息內容對象
     * @param dst 發送目標
     */
    void sendMessageToUser(Message message, Connection dst);


    /**
     * 給特定用戶發消息
     * @param message 消息內容對象
     * @param src 消息來源
     * @param dst 發送目標
     */
    void sendMessageToUser(Message message, Connection src, Connection dst);

}
