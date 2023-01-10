package com.demo.webrtc.domain.vo.sysmgr;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    @NotBlank(message = "用戶名不能為空")
    private String username;

    @Valid
    @NotBlank(message = "密碼不能為空")
    private String password;

    private String remember;

}
