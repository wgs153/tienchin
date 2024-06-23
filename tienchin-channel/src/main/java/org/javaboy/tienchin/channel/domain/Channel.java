package org.javaboy.tienchin.channel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.javaboy.tienchin.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 渠道表
 * </p>
 *
 * @author javaboy
 * @since 2024-05-20
 */
@Data
@TableName("tienchin_channel")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "channel_id", type = IdType.AUTO)
    @Excel(name = "渠道序号", cellType = Excel.ColumnType.NUMERIC)
    private Integer channelId;

    /**
     * 渠道名称
     */
    @Excel(name = "渠道名称")
    private String channelName;

    /**
     * 渠道状态
     */
    @Excel(name = "渠道状态", readConverterExp = "1=正常,0=禁用")
    private Byte status;

    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String remark;

    /**
     * 渠道类型：1线上渠道2：线下渠道
     */
    @Excel(name = "渠道类型", readConverterExp = "1=线上渠道,2=线下渠道")
    private Integer type;

    private String createBy;

    private String updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 删除标志
     */
    private Integer delFlag;

}
