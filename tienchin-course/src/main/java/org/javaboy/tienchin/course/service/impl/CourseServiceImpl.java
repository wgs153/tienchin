package org.javaboy.tienchin.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.javaboy.tienchin.course.domain.Course;
import org.javaboy.tienchin.course.domain.vo.CourseVO;
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
    public List<Course> selectCourseList(CourseVO courseVO) {
        return courseMapper.selectCourseList(courseVO);
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

    @Override
    public AjaxResult updateCourse(Course course) {
        course.setUpdateBy(SecurityUtils.getUsername());

        return updateById(course) ? AjaxResult.success("修改成功") : AjaxResult.error("修改失败");
    }

    @Override
    public AjaxResult getCourseById(Long courseId) {
        QueryWrapper<Course> qw = new QueryWrapper<>();
        LambdaQueryWrapper<Course> lqw = qw.lambda().eq(Course::getCourseId, courseId).eq(Course::getDelFlag, 0);
        Course course = getOne(lqw);
        return  course!=null ? AjaxResult.success(course) : AjaxResult.error("查询结果为空");
    }

    @Override
    public boolean deleteCourseById(Long[] courseIds) {
        LambdaUpdateWrapper<Course> luw = new UpdateWrapper<Course>().lambda();
        luw.in(Course::getCourseId,courseIds).set(Course::getDelFlag,1);
        return update(luw);
    }

}
