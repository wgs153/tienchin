package org.javaboy.tienchin.channel.service.impl;

import org.javaboy.tienchin.channel.domain.Channel;
import org.javaboy.tienchin.channel.mapper.ChannelMapper;
import org.javaboy.tienchin.channel.service.IChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 渠道表 服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2024-05-20
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {

    @Autowired
    ChannelMapper channelMapper;
    @Override
    public List<Channel> selectChannelList() {
        return channelMapper.selectChannelList();
    }
}
