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
 * 
 * </p>
 *
 * @author jacky917
 * @since 2023-01-02
 */
@Getter
@Setter
@TableName("wr_post")
public class WrPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主鍵
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 帖子標題
     */
    private String title;

    /**
     * 帖子內容
     */
    private String content;

    /**
     * 帖子狀態
     */
    private String status;

    /**
     * 發佈人ID
     */
    private Integer postUserId;

    /**
     * 帖子分類
     */
    private Integer postCategoryId;

    /**
     * 帖子標籤
     */
    private Integer postTagId;

    /**
     * 精選帖子
     */
    private Integer nicePost;

    /**
     * 瀏覽數
     */
    private Integer browseCount;

    /**
     * 點讚數
     */
    private Integer thumbsUp;

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
