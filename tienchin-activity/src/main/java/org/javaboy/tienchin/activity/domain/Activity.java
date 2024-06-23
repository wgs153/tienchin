package org.javaboy.tienchin.activity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 活动表
 * </p>
 *
 * @author javaboy
 * @since 2024-06-01
 */
@Data
@TableName("tienchin_activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId(value = "activity_id", type = IdType.AUTO)
    private Integer activityId;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 活动简介
     */
    private String info;

    /**
     * 活动类型
     */
    private Integer type;

    /**
     * 折扣券
     */
    private Double discount;

    /**
     * 代金券
     */
    private Double voucher;

    /**
     * 活动状态 0禁用 1正常
     */
    private Integer status;

    /**
     * 活动开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 活动结束时间
     */
    private LocalDateTime endTime;

    /**
     * 活动备注
     */
    private String remark;
    /**
     * 删除标志
     */
    private Integer delFlag;

    /**
     * 创建者
     */
    private String createBy;

    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    private LocalDateTime updateTime;

}
