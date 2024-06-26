package org.javaboy.tienchin.clue.service;

import org.javaboy.tienchin.clue.domain.Clue;
import com.baomidou.mybatisplus.extension.service.IService;
import org.javaboy.tienchin.common.core.domain.AjaxResult;

/**
 * <p>
 * 线索表 服务类
 * </p>
 *
 * @author javaboy
 * @since 2024-06-24
 */
public interface IClueService extends IService<Clue> {

    AjaxResult addClue(Clue clue);
}
