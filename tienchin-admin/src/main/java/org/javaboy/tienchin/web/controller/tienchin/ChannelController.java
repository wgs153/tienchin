package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.channel.domain.Channel;
import org.javaboy.tienchin.channel.domain.vo.ChannelVO;
import org.javaboy.tienchin.channel.service.IChannelService;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.constant.UserConstants;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.domain.entity.SysUser;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.javaboy.tienchin.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加渠道
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:create')")
    @Log(title = "渠道管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ChannelVO channelVO) {

        return channelService.addChannel(channelVO);
    }
}
