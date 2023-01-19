package com.demo.webrtc.service.impl;

import com.alibaba.fastjson.JSON;
import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.domain.entity.Message;
import com.demo.webrtc.service.ConnectionService;
import com.demo.webrtc.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    ConnectionService connectionService;

    @Override
    public void sendMessageToEveryOne(String command, String message) {

        Map<String, Connection> users = connectionService.getAllConnections();
        users.forEach((aLong, connection) -> {
            this.sendMessageToUser(new Message(command,message), connection);
        });
    }

    @Override
    public void sendMessageToEveryOneExcludeSelf(String command, String message, Connection self) {

        Map<String, Connection> users = connectionService.getAllConnections();
        users.forEach((aLong, connection) -> {
            if(!connection.getUser().getId().equals(self.getUser().getId()))
                this.sendMessageToUser(new Message(command,message), connection);
        });
    }

    @Override
    public void sendMessageToUser(Message message, Connection dst) {
        try {
            dst.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
        } catch (IOException e) {
            log.error("發送至 {} 的消息發送錯誤: , {}", dst.getUser().getAccount(), e);
        }
    }

    @Override
    public void sendMessageToUser(Message message,Connection src, Connection dst) {
        try {
            message.setUserName(src.getUser().getAccount());
            dst.getSession().getBasicRemote().sendText(JSON.toJSONString(message));
        } catch (IOException e) {
            log.error("發送至 {} 的消息發送錯誤: , {}", dst.getUser().getAccount(), e);
        }
    }
}
