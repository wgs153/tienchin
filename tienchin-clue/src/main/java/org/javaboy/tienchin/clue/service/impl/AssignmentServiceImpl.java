package org.javaboy.tienchin.clue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.clue.domain.Assignment;
import org.javaboy.tienchin.clue.mapper.AssignmentMapper;
import org.javaboy.tienchin.clue.service.IAssignmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.common.constant.TienChinConstants;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 分配表 服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2024-06-24
 */
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment> implements IAssignmentService {

    @Autowired
    AssignmentMapper assignmentMapper;



    @Override
    @Transactional
    public AjaxResult assignClue(Assignment assignment) {
        try {
//        将该线索的所有分配记录中的latest位置为false
            UpdateWrapper<Assignment> uw = new UpdateWrapper<>();
            uw.lambda().eq(Assignment::getAssignId, assignment.getAssignId()).set(Assignment::getLatest, Boolean.FALSE);
            update(uw);
//                    分配线索
            assignment.setLatest(true);
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setType(TienChinConstants.CLUE_TYPE);
            save(assignment);

            return AjaxResult.success("分配线索成功");
        } catch (Exception e) {
            return AjaxResult.error("分配线索失败"+e.getMessage());
        }

    }
}
