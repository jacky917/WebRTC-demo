package com.demo.webrtc.config;


import com.demo.webrtc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * WebSocket 初始化配置
 */

@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    /**
     * 開啟WebSocket
     * @return
     */

    @Autowired
    AccountService accountService;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
//        System.out.println("======================================");
//        System.out.println(request.getUserPrincipal());
//        System.out.println(request.isUserInRole("someRole"));
//        System.out.println("======================================");
//        config.getUserProperties().put("UserPrincipal",request.getUserPrincipal());
//        config.getUserProperties().put("userInRole", request.isUserInRole("someRole"));
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        config.getUserProperties().put("clientIp", httpSession.getAttribute("clientIp"));
        super.modifyHandshake(config, request, response);
    }
}

