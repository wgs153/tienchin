package org.javaboy.tienchin.clue.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Author eqe
 * @Date 2024/6/26 22:21
 * @description: 摘要
 */
@Data
public class ClueSummary {

    private Integer clueId;

    /**
     * 客户名
     */
    private String name;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 线索状态 1：已分配，2：跟进中 ，3：回收 4：伪线索
     */
    private Integer status;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String createBy;

    /**
     * 跟进人员
     */
    private String owner;
    private String nextTime;
}
