package org.javaboy.tienchin.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.activity.domain.Activity;
import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.activity.mapper.ActivityMapper;
import org.javaboy.tienchin.activity.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.javaboy.tienchin.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.Channel;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2024-06-01
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    ActivityMapper activityMapper;
    @Override
    public List<ActivityVO> selectActivityList(ActivityVO activityVO) {
        expireActivity();
        return activityMapper.selectActivityList(activityVO);
    }

    /**
     * 根据当前时间判断活动是否已经结束
     * @return
     */
    private boolean expireActivity() {
        UpdateWrapper<Activity> uw = new UpdateWrapper<>();
        uw.lambda().set(Activity::getStatus, 0).eq(Activity::getStatus, 1).lt(Activity::getEndTime, LocalDateTime.now());
        return update(uw);
    }

    @Override
    public AjaxResult addActivity(ActivityVO activityVO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO, activity);
        // 默认状态为进行中
        activity.setStatus(1);
        return save(activity) ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }

    @Override
    public AjaxResult updateActivity(ActivityVO activityVO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO, activity);
        activity.setUpdateBy(SecurityUtils.getUsername());
        return updateById(activity)?AjaxResult.success("修改成功") : AjaxResult.error("修改失败");
    }

    @Override
    public ActivityVO getActivityVOById(Integer activityId) {
        Activity activity = getById(activityId);
        ActivityVO activityVO = new ActivityVO();
        BeanUtils.copyProperties(activity, activityVO);
        return activityVO;
    }

    @Override
    public boolean deleteActivityByIds(Long[] activityIds) {
        UpdateWrapper<Activity> uw = new UpdateWrapper<>();
        uw.lambda().set(Activity::getDelFlag,1).in(Activity::getActivityId, activityIds);
        return update(uw);
    }

    @Override
    public AjaxResult getActivityByChannelId(Integer channelId) {
        LambdaQueryWrapper<Activity> lqw = new QueryWrapper<Activity>().lambda();
        lqw.eq(Activity::getChannelId, channelId);
        return AjaxResult.success(list(lqw));
    }

}
