package org.javaboy.tienchin.channel.service;

import org.javaboy.tienchin.channel.domain.Channel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.javaboy.tienchin.channel.domain.vo.ChannelVO;
import org.javaboy.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 * 渠道表 服务类
 * </p>
 *
 * @author javaboy
 * @since 2024-05-20
 */
public interface IChannelService extends IService<Channel> {

    List<Channel> selectChannelList();

    AjaxResult addChannel(ChannelVO channelVO);
}
