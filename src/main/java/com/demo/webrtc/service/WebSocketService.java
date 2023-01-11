package com.demo.webrtc.service;

import com.demo.webrtc.domain.entity.Connection;

import java.util.Map;

public interface WebSocketService {

    /**
     * 獲取WebSocket公網請求URL
     * @return URL
     */
    String getWebSocketURL();

    /**
     * WebSocket連接建立成功
     * @param connection 當前用戶
     */
    void onOpen(Connection connection);

    /**
     * WebSocket關閉連接
     * @param connection 當前用戶
     */
    void onClose(Connection connection);

    /**
     * WebSocket發生錯誤
     * @param connection 當前用戶
     * @param error 錯誤
     */
    void onError(Connection connection, Throwable error);

    /**
     * WebSocket獲取到客戶端消息
     * @param message 客戶端發過來的消息
     */
    void onMessage(Connection connection, String message);

    /**
     * WebSocket向所有在線用戶推送消息(Session池存在的用戶)
     * @param message 要向客戶端推送的消息
     */
    void pushMessage(String message);

    /**
     * WebSocket向指定的一個用戶推送消息
     * @param message 要向客戶端推送的消息
     */
    void pushMessage(Connection connection, String message);

    /**
     * WebSocket向指定多用戶推送消息
     * @param message 要向客戶端推送的消息
     */
    void pushMessage(Map<String,Connection> connMap, String message);
}
