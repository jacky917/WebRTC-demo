package com.demo.webrtc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.demo.webrtc.mapper")
public class WebRtcApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebRtcApplication.class, args);
    }

}
