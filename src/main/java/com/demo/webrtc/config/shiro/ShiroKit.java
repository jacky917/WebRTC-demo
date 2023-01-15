package com.demo.webrtc.config.shiro;

import com.demo.webrtc.domain.entity.WrUser;
import org.apache.shiro.SecurityUtils;

public class ShiroKit {

    /**
     * 返回當前用戶對象
     *
     * @return 當前用戶信息
     */
    public static WrUser getCurrentUser() {
        if (SecurityUtils.getSubject() != null) {
            return (WrUser)SecurityUtils.getSubject().getPrincipal();
        }
        return null;
    }

}
