package org.javaboy.tienchin.activity.mapper;

import org.javaboy.tienchin.activity.domain.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.javaboy.tienchin.activity.domain.vo.ActivityVO;

import java.util.List;

/**
 * <p>
 * 活动表 Mapper 接口
 * </p>
 *
 * @author javaboy
 * @since 2024-06-01
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    List<ActivityVO> selectActivityList(ActivityVO activityVO);

}
