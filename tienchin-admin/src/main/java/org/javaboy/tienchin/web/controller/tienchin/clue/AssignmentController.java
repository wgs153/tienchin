package org.javaboy.tienchin.web.controller.tienchin.clue;

import org.javaboy.tienchin.clue.domain.Assignment;
import org.javaboy.tienchin.clue.service.IAssignmentService;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 分配表 前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-06-24
 */
@RestController
@RequestMapping("/tienchin/assignment")
public class AssignmentController {

    @Autowired
    IAssignmentService assignmentService;

    @PostMapping
    @PreAuthorize("@ss.hasPermi('tienchin:clue:assignment')")
    public AjaxResult assignClue(@Validated @RequestBody Assignment assignment) {
        return assignmentService.assignClue(assignment);
    }

}
