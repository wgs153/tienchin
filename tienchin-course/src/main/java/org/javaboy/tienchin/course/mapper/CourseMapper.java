package org.javaboy.tienchin.course.mapper;

import org.javaboy.tienchin.course.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author javaboy
 * @since 2024-06-18
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> selectCourseList();
}
