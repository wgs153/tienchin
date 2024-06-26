package org.javaboy.tienchin.clue.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 分配表
 * </p>
 *
 * @author javaboy
 * @since 2024-06-24
 */
@Data
@TableName("tienchin_assignment")
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    /**
     * 1：线索，2：商机
     */
    private Integer type;

    /**
     * 线索或商机的ID
     */
//    private Integer assignId;
    private Integer assignId;

    /**
     * 所属用户的id
     */
    private Long userId;

    /**
     * 所属的用户名
     */
    private String userName;

    /**
     * 所属部门ID
     */
    private Long deptId;

    /**
     * 是否是当前最新分配人
     */
    private Boolean latest;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;

}
