package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.activity.service.IActivityService;
import org.javaboy.tienchin.common.validator.CreateGroup;
import org.javaboy.tienchin.common.validator.EditGroup;
import org.javaboy.tienchin.channel.domain.vo.ChannelVO;
import org.javaboy.tienchin.channel.service.IChannelService;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    IChannelService  channelService;

    /**
     * 查询活动列表
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityVO activityVO) {
        startPage();
        List<ActivityVO> list = activityService.selectActivityList(activityVO);
        return getDataTable(list);
    }
    /**
     * 添加活动弹窗-查询渠道下拉框
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:create')")
    @GetMapping("/channel/list")
    public AjaxResult channelList() {
        ChannelVO channelVO = new ChannelVO();
        channelVO.setDelFlag(0);
        return AjaxResult.success(channelService.selectChannelList(channelVO));
    }

    /**
     * 添加活动
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:create')")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody ActivityVO activityVO){
        return activityService.addActivity(activityVO);
    }

    /**
     * 修改活动
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:edit')")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult update(@Validated(EditGroup.class) @RequestBody ActivityVO activityVO){
        return activityService.updateActivity(activityVO);
    }

    /**
     * 修改活动时查询活动详情
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:edit')")
    @GetMapping("/{activityId}")
    public AjaxResult getInfo(@PathVariable Integer activityId){

        return AjaxResult.success(activityService.getActivityVOById(activityId));
    }

    /**
     * 删除活动
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:remove')")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult deleteById(@PathVariable Long[] activityIds){
        return toAjax(activityService.deleteActivityByIds(activityIds));
    }

    /**
     * 导出活动
     * @param response
     * @param activityVO
     */
    @Log(title = "活动管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:activity:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityVO activityVO) {
        List<ActivityVO> list = activityService.selectActivityList(activityVO);
        ExcelUtil<ActivityVO> util = new ExcelUtil<ActivityVO>(ActivityVO.class);
        util.exportExcel(response, list, "活动数据");
    }


}
