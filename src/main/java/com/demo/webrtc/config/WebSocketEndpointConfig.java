package com.demo.webrtc.config;

import com.demo.webrtc.config.shiro.ShiroKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * 在modifyHandshake方法中，可以将储存在httpSession中的clientIp，转移到ServerEndpointConfig中
 */
@Slf4j
@Configuration
public class WebSocketEndpointConfig extends ServerEndpointConfig.Configurator {

    /**
     * 把HttpSession中保存的ClientIP放到ServerEndpointConfig中
     * @param config
     * @param request
     * @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        config.getUserProperties().put("clientIp", httpSession.getAttribute("clientIp"));

        if(ShiroKit.getCurrentUser() != null)
            config.getUserProperties().put("user", ShiroKit.getCurrentUser());
        super.modifyHandshake(config, request, response);
    }
}
