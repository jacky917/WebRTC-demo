package com.demo.webrtc.bean;

import com.demo.webrtc.domain.entity.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class SessionPool {

    @Bean
    public HashMap<String, Connection> getSessions(){
        return new HashMap<>();
    }
}
