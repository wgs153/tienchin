package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.activity.service.IActivityService;
import org.javaboy.tienchin.channel.domain.vo.ChannelVO;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-06-01
 */
@RestController
@RequestMapping("/tienchin/activity")
public class ActivityController extends BaseController {
    @Autowired
    IActivityService activityService;
    /**
     * 查询活动列表
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<ActivityVO> list = activityService.selectActivityList();
        return getDataTable(list);
    }

    /**
     * 添加活动
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:create')")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ActivityVO activityVO){
        return activityService.addActivity(activityVO);
    }


}
