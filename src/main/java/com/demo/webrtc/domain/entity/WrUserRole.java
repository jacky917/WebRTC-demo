package com.demo.webrtc.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author jacky917
 * @since 2023-01-02
 */
@Getter
@Setter
@TableName("wr_user_role")
public class WrUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主鍵
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用戶ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 創建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String editor;

    /**
     * 有效標示(1:有效,0:無效)
     */
    private String ynFlag;

    /**
     * 創建時間
     */
    private LocalDateTime createdTime;

    /**
     * 修改時間
     */
    private LocalDateTime modifiedTime;


}
