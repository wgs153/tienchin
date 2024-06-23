package org.javaboy.tienchin.course.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getApplyTo() {
        return applyTo;
    }

    public void setApplyTo(Integer applyTo) {
        this.applyTo = applyTo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Course{" +
            "courseId = " + courseId +
            ", type = " + type +
            ", name = " + name +
            ", price = " + price +
            ", applyTo = " + applyTo +
            ", info = " + info +
            ", remark = " + remark +
            ", createBy = " + createBy +
            ", updateBy = " + updateBy +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", delFlag = " + delFlag +
        "}";
    }
}
