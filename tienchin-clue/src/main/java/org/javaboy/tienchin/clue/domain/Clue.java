package org.javaboy.tienchin.clue.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 线索表
 * </p>
 *
 * @author javaboy
 * @since 2024-06-24
 */
@Data
@TableName("tienchin_clue")
public class Clue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "clue_id", type = IdType.AUTO)
    private Integer clueId;

    /**
     * 客户名
     */
    @NotBlank(message = "{clue.name.NotBlank}")
    private String name;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 活动id
     */
    private Integer activityId;

    /**
     * 性别，0:男，1：女
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 客户微信号
     */
    private String weixin;

    /**
     * 客户qq
     */
    private String qq;

    /**
     * 客户电话
     */
    @NotBlank(message = "{clue.phone.NotBlank}")
    private String phone;

    /**
     * 客户意向等级，1：近期报名，2：打算报名，考虑中，3：了解一下，4：打酱油
     */
    private Integer level;

    /**
     * 私教课程
     */
    private Integer subject;

    /**
     * 线索状态 1：已分配，2：跟进中 ，3：回收 4：伪线索
     */
    private Integer status;

    /**
     * 伪线索失败次数，最大3次
     */
    private Integer fallCount;

    /**
     * 下次跟进时间
     */
    private LocalDateTime nextTime;

    /**
     * 线索失效时间
     */
    private LocalDateTime endTime;

    /**
     * 线索是否需要转派
     */
    private Byte transfer;

    /**
     * 备注
     */
    private String remark;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

}
