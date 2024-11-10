package org.javaboy.tienchin.clue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.javaboy.tienchin.clue.domain.Assignment;
import org.javaboy.tienchin.clue.domain.Clue;
import org.javaboy.tienchin.clue.domain.vo.ClueSummary;
import org.javaboy.tienchin.clue.mapper.ClueMapper;
import org.javaboy.tienchin.clue.service.IAssignmentService;
import org.javaboy.tienchin.clue.service.IClueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.common.constant.TienChinConstants;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    IAssignmentService assignmentService;

    @Autowired
    ClueMapper clueMapper;

    @Override
    @Transactional
    public AjaxResult addClue(Clue clue){
        QueryWrapper<Clue> qw = new QueryWrapper<Clue>();
        qw.lambda().eq(Clue::getPhone, clue.getPhone());
        Clue one = getOne(qw);
        if (one != null) {
            return AjaxResult.error("手机号码重复，线索录入失败");
        }
        clue.setCreateBy(SecurityUtils.getUsername());
        clue.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOW_TIME));
        clue.setStatus(1);
        try{
            // 添加线索
            save(clue);
            // 添加默认的分配人
            Assignment assignment = new Assignment();
            assignment.setAssignId(clue.getClueId());
            assignment.setLatest(true);
            assignment.setType(TienChinConstants.CLUE_TYPE);
            assignment.setUserName(SecurityUtils.getUsername());
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setUserId(SecurityUtils.getUserId());
            assignment.setDeptId(SecurityUtils.getDeptId());
            assignment.setCreateTime(LocalDateTime.now());
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignmentService.save(assignment);
            return AjaxResult.success("线索录入成功");
        }catch (Exception e){
            // 手动触发事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("线索录入失败", e);
            return AjaxResult.error("线索录入失败");
        }
    }

    @Override
    public List<ClueSummary> selectClueList() {
        return clueMapper.selectClueList();
    }


}
