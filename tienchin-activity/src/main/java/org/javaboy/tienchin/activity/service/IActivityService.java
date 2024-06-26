package org.javaboy.tienchin.activity.service;

import org.javaboy.tienchin.activity.domain.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author javaboy
 * @since 2024-06-01
 */
public interface IActivityService extends IService<Activity> {

    List<ActivityVO> selectActivityList(ActivityVO activityVO);

    AjaxResult addActivity(ActivityVO activityVO);

    AjaxResult updateActivity(ActivityVO activityVO);

    ActivityVO getActivityVOById(Integer activityId);

    boolean deleteActivityByIds(Long[] activityIds);

    AjaxResult getActivityByChannelId(Integer channelId);
}
