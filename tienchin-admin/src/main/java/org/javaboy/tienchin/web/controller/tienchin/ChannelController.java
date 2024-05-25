package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.channel.domain.Channel;
import org.javaboy.tienchin.channel.service.IChannelService;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 渠道表 前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/tienchin/channel")
public class ChannelController extends BaseController {

    @Autowired
    IChannelService channelService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<Channel> list = channelService.selectChannelList();
        return getDataTable(list);
    }
}
