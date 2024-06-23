package org.javaboy.tienchin.channel.domain.vo;

import lombok.Data;
import org.javaboy.tienchin.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author eqe
 * @Date 2024/5/25 21:45
 * @description: vo
 */
@Data
public class ChannelVO extends BaseEntity {

    private Integer channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道状态
     */
    private Byte status;

    /**
     * 渠道类型：1线上渠道2：线下渠道
     */
    @NotNull(message = "{channel.type.notnull}")
    private Integer type;

    /**
     * 删除标志
     */
    private Integer delFlag;

}
