package org.javaboy.tienchin.course.service.impl;

import org.javaboy.tienchin.course.domain.Course;
import org.javaboy.tienchin.course.mapper.CourseMapper;
import org.javaboy.tienchin.course.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2024-06-18
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
