package com.demo.webrtc.service.impl;

import com.alibaba.fastjson.JSON;
import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.domain.entity.Message;
import com.demo.webrtc.service.MessageService;
import com.demo.webrtc.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    RoomService roomService;

    @Override
    public void sendMessageToEveryOne(Message message) {
        Map<Long, Connection> users = roomService.getCurrentOnlineUsers();
        users.forEach((aLong, connection) -> {
            this.sendMessageToUser(message, connection);
        });
    }

    @Override
    public void sendMessageToEveryOneExcludeSelf(Message message, Connection self) {
        Map<Long, Connection> users = roomService.getCurrentOnlineUsers();
        users.forEach((aLong, connection) -> {
            if(!connection.getUser().getId().equals(self.getUser().getId()))
                this.sendMessageToUser(message, connection);
        });
    }

    @Override
    public void sendMessageToUser(Message message, Connection connection) {
        try {
            connection.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
        } catch (IOException e) {
            log.error("發送消息失敗: {}, {}", message.getUserId(), e);
        }
    }
}
