package org.javaboy.tienchin.clue.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 线索跟进记录表
 * </p>
 *
 * @author javaboy
 * @since 2024-06-24
 */
@Data
@TableName("tienchin_follow_record")
public class FollowRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    /**
     * 跟进记录类型 1：线索 2：商机
     */
    private Integer type;

    /**
     * 线索或商机的ID
     */
    private Integer assignId;

    private String info;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

}
