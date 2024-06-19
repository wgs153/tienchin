package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.course.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.javaboy.tienchin.course.service.ICourseService;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-06-18
 */
@RestController
@RequestMapping("/tienchin/course")
public class CourseController extends BaseController {

    @Autowired
    ICourseService courseService;

    /**
     * 查询课程列表
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<Course> list = courseService.selectCourseList();
        return getDataTable(list);
    }
}
