package org.javaboy.tienchin.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.javaboy.tienchin.course.domain.Course;
import org.javaboy.tienchin.course.mapper.CourseMapper;
import org.javaboy.tienchin.course.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private static final Logger logger=LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Course> selectCourseList() {
        return courseMapper.selectCourseList();
    }

    @Override
    public AjaxResult addCourse(Course course) {
        //判断是否有同名课程
        LambdaQueryWrapper<Course> eq = new QueryWrapper<Course>().lambda().eq(Course::getName, course.getName()).eq(Course::getDelFlag, 0);
        //getOne是mybatisPlus提供的方法，参数是Wraapper
        Course one = getOne(eq);
        if (one!=null){
            logger.info("课程名已存在");
            return AjaxResult.error("课程名已存在");
        }
        course.setCreateBy(SecurityUtils.getUsername());
        return save(course) ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }

}
