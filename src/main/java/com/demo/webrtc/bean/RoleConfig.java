package com.demo.webrtc.bean;


import com.demo.webrtc.domain.entity.WrRole;
import com.demo.webrtc.mapper.WrRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@Configuration
public class RoleConfig {

    @Autowired
    WrRoleMapper roleMapper;

    @Bean
    public HashMap<String, WrRole> RoleName(){
        List<WrRole> wrRoles = roleMapper.selectList(null);
        HashMap<String, WrRole> hashMap = new HashMap<>();
        for(WrRole role : wrRoles){
            hashMap.put(role.getId().toString(),role);
        }
        return hashMap;
    }

}
