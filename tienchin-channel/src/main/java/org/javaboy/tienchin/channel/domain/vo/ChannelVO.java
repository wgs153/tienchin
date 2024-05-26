package org.javaboy.tienchin.channel.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.javaboy.tienchin.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author eqe
 * @Date 2024/5/25 21:45
 * @description: vo
 */
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



    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
    @NotBlank(message = "{channel.name.notnull}")
    @Size(min = 0, max = 10, message = "渠道名称长度不能超过10个字符")
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @NotNull(message = "{channel.status.notnull}")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
