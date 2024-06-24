package org.javaboy.tienchin.web.controller.tienchin;

import org.apache.ibatis.annotations.Delete;
import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.common.utils.poi.ExcelUtil;
import org.javaboy.tienchin.common.validator.CreateGroup;
import org.javaboy.tienchin.common.validator.EditGroup;
import org.javaboy.tienchin.course.domain.Course;
import org.javaboy.tienchin.course.domain.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.javaboy.tienchin.course.service.ICourseService;

import javax.servlet.http.HttpServletResponse;
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
    public TableDataInfo list(CourseVO courseVO) {
        startPage();
        List<Course> list = courseService.selectCourseList(courseVO);
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

    /**
     * 删除课程
     * @param courseIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:remove')")
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public boolean deleteCourse(@PathVariable Long[] courseIds){
        return courseService.deleteCourseById(courseIds);
    }
    /**
     * 导出课程
     * @param response
     * @param courseVO
     */
    @Log(title = "课程管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:course:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseVO courseVO) {
        List<Course> list = courseService.selectCourseList(courseVO);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        util.exportExcel(response, list, "课程数据");
    }


}
