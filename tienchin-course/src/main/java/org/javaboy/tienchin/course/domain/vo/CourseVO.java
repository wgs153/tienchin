package org.javaboy.tienchin.course.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.javaboy.tienchin.common.annotation.Excel;

/**
 * @Author eqe
 * @Date 2024/6/24 19:49
 * @description: vo
 */
@Data
public class CourseVO {

    private Integer courseId;

    private String name;

    private Integer type;

    private Integer applyTo;
    /**
     * 最低价格
     */
    private Double minPrice;
    /**
     * 最高价格
     */
    private Double maxPrice;

}
