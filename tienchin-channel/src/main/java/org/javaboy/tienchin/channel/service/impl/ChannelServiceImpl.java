package org.javaboy.tienchin.channel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.channel.domain.Channel;
import org.javaboy.tienchin.channel.domain.vo.ChannelVO;
import org.javaboy.tienchin.channel.mapper.ChannelMapper;
import org.javaboy.tienchin.channel.service.IChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Channel> selectChannelList(ChannelVO channelVO) {
        return channelMapper.selectChannelList(channelVO);
    }

    @Override
    public AjaxResult addChannel(ChannelVO channelVO) {
        QueryWrapper<Channel> qw = new QueryWrapper<>();
        qw.lambda().eq(Channel::getChannelName,channelVO.getChannelName()).eq(Channel::getDelFlag,0);
        Channel c = getOne(qw);
        if(c!=null){
//            说明存在一个同名的渠道，并且没有删除
            return AjaxResult.error("渠道名已存在");
        }
        //不存在同名的渠道，可以添加
        Channel channel = new Channel();
        // 复制属性到channel中
        BeanUtils.copyProperties(channelVO,channel);
        channel.setDelFlag(0);

        return save(channel)?AjaxResult.success("添加成功"):AjaxResult.error("添加失败");
    }

    @Override
    public AjaxResult updateChannel(ChannelVO channelVO) {
        Channel channel = new Channel();
        BeanUtils.copyProperties(channelVO,channel);
        channel.setCreateTime(null);
        return updateById(channel)?AjaxResult.success("修改成功"):AjaxResult.error("修改失败");
    }

    @Override
    public boolean deleteChannelByIds(Long[] channelIds) {
        UpdateWrapper<Channel> uw = new UpdateWrapper<>();
        uw.lambda().set(Channel::getDelFlag,1).in(Channel::getChannelId,channelIds);
        return update(uw);
    }

    @Override
    public boolean importChannel(List<Channel> channelList, boolean updateSupport) {
        if (updateSupport){
            //设置更新用户名
            List<Channel> channels = channelList.stream().map(s -> {
                s.setUpdateBy(SecurityUtils.getUsername());
                return s;
            }).collect(Collectors.toList());
            //根据id更新
            updateBatchById(channels);
        }else {
            //设置创建用户名 更新用户名
            List<Channel> channels = channelList.stream().map(s -> {
                s.setCreateBy(SecurityUtils.getUsername());
                s.setChannelId(null);
                return s;
            }).collect(Collectors.toList());
            //批量插入
            saveBatch(channels);
        }

        return false;
    }
}
