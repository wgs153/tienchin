package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.activity.validator.CreateGroup;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.course.domain.Course;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @PreAuthorize("@ss.hasPermi('tienchin:course:create')")
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult addCourse(@Validated @RequestBody Course course){


        return courseService.addCourse(course);
    }


}
