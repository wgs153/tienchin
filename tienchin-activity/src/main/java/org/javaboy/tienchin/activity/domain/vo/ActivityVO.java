package org.javaboy.tienchin.activity.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.javaboy.tienchin.activity.validator.CreateGroup;
import org.javaboy.tienchin.activity.validator.EditGroup;
import org.javaboy.tienchin.common.core.domain.BaseEntity;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * <p>
 * 活动表
 * </p>
 *
 * @author javaboy
 * @since 2024-06-01
 */

public class ActivityVO extends BaseEntity {

    /**
     * 序号
     */
    @NotNull(message = "{activity.activityId.NotNull}",groups = {EditGroup.class})
    private Integer activityId;

    /**
     * 活动名称
     */
    @NotBlank(message = "{activity.name.NotBlank}",groups = {CreateGroup.class,EditGroup.class})
    @Size(min = 0, max = 5, message = "{activity.name.Size}",groups = {CreateGroup.class,EditGroup.class})
    private String name;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 活动简介
     */
    @NotBlank(message = "{activity.info.NotBlank}",groups = {CreateGroup.class,EditGroup.class})
    @Size(min = 0, max = 225, message = "{activity.info.Size}",groups = {CreateGroup.class,EditGroup.class})
    private String info;

    /**
     * 活动类型
     */
    @NotNull(message = "{activity.type.NotNull}",groups = {CreateGroup.class,EditGroup.class})
    private Integer type;

    /**
     * 折扣券
     */
    @Min(value=0,message = "{activity.discount.Min}",groups = {CreateGroup.class,EditGroup.class})
    @Max(value=10,message = "{activity.discount.Max}",groups = {CreateGroup.class,EditGroup.class})
    private Double discount;

    /**
     * 代金券
     */
    @Min(value=0,message = "{activity.voucher.Min}",groups = {CreateGroup.class,EditGroup.class})
    private Double voucher;

    /**
     * 活动状态 0禁用 1正常
     */
    @Min(value=0,message = "{activity.status.Min}",groups = {CreateGroup.class,EditGroup.class})
    @Max(value=1,message = "{activity.status.Max}",groups = {CreateGroup.class,EditGroup.class})
    private Integer status;

    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{activity.beginTime.NotNull}",groups = {CreateGroup.class,EditGroup.class})
    private LocalDateTime beginTime;

    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{activity.endTime.NotNull}",groups = {CreateGroup.class,EditGroup.class})
    private LocalDateTime endTime;

    /**
     * 删除标志
     */
    private Integer delFlag;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVoucher() {
        return voucher;
    }

    public void setVoucher(Double voucher) {
        this.voucher = voucher;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "ActivityVO{" +
                "activityId=" + activityId +
                ", name='" + name + '\'' +
                ", channelId=" + channelId +
                ", channelName=" + channelName +
                ", info='" + info + '\'' +
                ", type=" + type +
                ", discount=" + discount +
                ", voucher=" + voucher +
                ", status=" + status +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
