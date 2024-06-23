package org.javaboy.tienchin.course.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 课程
 * </p>
 *
 * @author javaboy
 * @since 2024-06-18
 */
@Data
@TableName("tienchin_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    /**
     * 1:舞蹈类 2:游泳类 3:拳击类
     */
    @NotNull(message = "{course.type.NotNull}")
    private Integer type;

    @NotBlank(message = "{course.name.NotBlank}")
    private String name;

    @NotNull(message = "{course.price.NotNull}")
    @Min(value = 0,message = "{course.price.Min}")
    private Double price;

    /**
     * 课程适用人群
     */
    @NotNull(message = "{course.applyTo.NotBlank}")
    private Integer applyTo;

    /**
     * 课程简介
     */
    @NotBlank(message = "{course.info.NotBlank}")
    private String info;

    /**
     * 备注信息
     */
    private String remark;

    private String createBy;

    private String updateBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 0:存在 1：删除
     */
    private Integer delFlag;

}
