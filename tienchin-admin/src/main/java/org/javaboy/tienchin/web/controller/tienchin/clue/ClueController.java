package org.javaboy.tienchin.web.controller.tienchin.clue;

import org.javaboy.tienchin.clue.domain.Clue;
import org.javaboy.tienchin.clue.service.IClueService;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 * 线索表 前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-06-24
 */
@RestController
@RequestMapping("/tienchin/clue")
public class ClueController {

    @Autowired
    IClueService clueService;

    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @Log(title = "线索管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult addClue(@Validated @RequestBody Clue clue) {
        return clueService.addClue(clue);
    }


}
