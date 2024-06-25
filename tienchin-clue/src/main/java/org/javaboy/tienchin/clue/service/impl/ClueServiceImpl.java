package org.javaboy.tienchin.clue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.javaboy.tienchin.clue.domain.Clue;
import org.javaboy.tienchin.clue.mapper.ClueMapper;
import org.javaboy.tienchin.clue.service.IClueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 线索表 服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2024-06-24
 */
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements IClueService {

    @Override
    public AjaxResult addClue(Clue clue) {
        QueryWrapper<Clue> qw = new QueryWrapper<Clue>();
        qw.lambda().eq(Clue::getPhone, clue.getPhone());
        Clue one = getOne(qw);
        if (one != null) {
            return AjaxResult.error("手机号码重复，线索录入失败");
        }
        return save(clue) ? AjaxResult.success("线索录入成功") : AjaxResult.error("线索录入失败");
    }
}
