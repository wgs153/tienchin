package org.javaboy.tienchin.web.controller.tienchin;

import org.apache.ibatis.annotations.Delete;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.common.validator.CreateGroup;
import org.javaboy.tienchin.common.validator.EditGroup;
import org.javaboy.tienchin.course.domain.Course;
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

    /**
     * 新增课程
     * @param course
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:create')")
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult addCourse(@Validated({CreateGroup.class}) @RequestBody Course course){
        return courseService.addCourse(course);
    }

    /**
     * 修改课程
     * @param course
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:edit')")
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateCourse(@Validated({EditGroup.class}) @RequestBody Course course){
        return courseService.updateCourse(course);
    }

    /**
     * 修改时，查询课程信息
     * @param courseId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:edit')")
    @GetMapping("/{courseId}")
    public AjaxResult getCourseById(@PathVariable Long courseId){
        return courseService.getCourseById(courseId);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:course:remove')")
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public boolean deleteCourse(@PathVariable Long[] courseIds){
        return courseService.deleteCourseById(courseIds);
    }

}
