package org.javaboy.tienchin.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.activity.domain.Activity;
import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.activity.mapper.ActivityMapper;
import org.javaboy.tienchin.activity.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<ActivityVO> selectActivityList() {
        expireActivity();
        return activityMapper.selectActivityList();
    }
    private boolean expireActivity() {
        UpdateWrapper<Activity> uw = new UpdateWrapper<>();
        uw.lambda().set(Activity::getStatus, 0).eq(Activity::getStatus, 1).lt(Activity::getEndTime, LocalDateTime.now());
        return update(uw);
    }
}
