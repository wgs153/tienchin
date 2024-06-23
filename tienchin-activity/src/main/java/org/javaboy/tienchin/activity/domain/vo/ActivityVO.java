package org.javaboy.tienchin.activity.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.javaboy.tienchin.common.validator.CreateGroup;
import org.javaboy.tienchin.common.validator.EditGroup;
import org.javaboy.tienchin.common.annotation.Excel;
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
@Data
public class ActivityVO extends BaseEntity {

    /**
     * 序号
     */
    @Excel(name = "活动序号", cellType = Excel.ColumnType.NUMERIC)
    @NotNull(message = "{activity.activityId.NotNull}",groups = {EditGroup.class})
    private Integer activityId;

    /**
     * 活动名称
     */
    @Excel(name = "活动名称")
    @NotBlank(message = "{activity.name.NotBlank}",groups = {CreateGroup.class,EditGroup.class})
    @Size(min = 0, max = 5, message = "{activity.name.Size}",groups = {CreateGroup.class,EditGroup.class})
    private String name;

    /**
     * 渠道id
     */
    @Excel(name = "渠道id")
    private Integer channelId;

    /**
     * 渠道名称
     */
    @Excel(name = "渠道名称")
    private String channelName;

    /**
     * 活动简介
     */
    @Excel(name = "活动简介")
    @NotBlank(message = "{activity.info.NotBlank}",groups = {CreateGroup.class,EditGroup.class})
    @Size(min = 0, max = 225, message = "{activity.info.Size}",groups = {CreateGroup.class,EditGroup.class})
    private String info;

    /**
     * 活动类型
     */
    @Excel(name = "活动类型",readConverterExp = "1=折扣券,2=代金券")
    @NotNull(message = "{activity.type.NotNull}",groups = {CreateGroup.class,EditGroup.class})
    private Integer type;

    /**
     * 折扣券
     */
    @Excel(name = "折扣券")
    @Min(value=0,message = "{activity.discount.Min}",groups = {CreateGroup.class,EditGroup.class})
    @Max(value=10,message = "{activity.discount.Max}",groups = {CreateGroup.class,EditGroup.class})
    private Double discount;

    /**
     * 代金券
     */
    @Excel(name = "代金券")
    @Min(value=0,message = "{activity.voucher.Min}",groups = {CreateGroup.class,EditGroup.class})
    private Double voucher;

    /**
     * 活动状态 0禁用 1正常
     */
    @Excel(name = "活动状态",readConverterExp = "0=过期,1=正常")
    @Min(value=0,message = "{activity.status.Min}",groups = {CreateGroup.class,EditGroup.class})
    @Max(value=1,message = "{activity.status.Max}",groups = {CreateGroup.class,EditGroup.class})
    private Integer status;

    /**
     * 活动开始时间
     */
    @Excel(name = "活动开始时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{activity.beginTime.NotNull}",groups = {CreateGroup.class,EditGroup.class})
    private LocalDateTime beginTime;

    /**
     * 活动结束时间
     */
    @Excel(name = "活动结束时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{activity.endTime.NotNull}",groups = {CreateGroup.class,EditGroup.class})
    private LocalDateTime endTime;

    /**
     * 备注
     */
    @Excel(name = "活动备注")
    private String remark;

    /**
     * 删除标志
     */
    private Integer delFlag;

}
