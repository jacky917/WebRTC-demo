package com.demo.webrtc.service;

import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.domain.entity.Message;

public interface MessageService{

    /**
     * 給所有人發消息
     * @param message 消息內容
     */
    void sendMessageToEveryOne(Message message);

    /**
     * 給除了自己以外的所有人發消息
     * @param message 消息內容
     * @param connection 自身的連接信息
     */
    void sendMessageToEveryOneExcludeSelf(Message message, Connection connection);

    /**
     * 給特定用戶發消息
     * @param message 消息內容
     * @param connection 目標用戶連接信息
     */
    void sendMessageToUser(Message message, Connection connection);

}
