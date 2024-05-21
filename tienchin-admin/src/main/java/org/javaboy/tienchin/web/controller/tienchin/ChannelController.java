package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.channel.service.IChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 渠道表 前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-05-20
 */
@RestController
@RequestMapping("/channel/channel")
public class ChannelController {

    @Autowired
    IChannelService channelService;
}
