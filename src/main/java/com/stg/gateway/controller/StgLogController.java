package com.stg.gateway.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stg.gateway.entity.StgLog;
import com.stg.gateway.service.StgLogService;
import com.stg.gateway.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author yq
 * @date 2021-04-19 10:00
 */
@RestController
@RequestMapping("/log")
public class StgLogController {

    @Autowired
    private StgLogService stgLogService;

    /**
     * 新增
     * @param stgLog
     * @return
     */
    @PostMapping
    public R save(@RequestBody StgLog stgLog) {
        stgLog.setTime(LocalDateTime.now());
        return R.ok(stgLogService.save(stgLog));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.ok(stgLogService.removeById(id));
    }

    /**
     * 修改
     * @param stgLog
     * @return
     */
    @PutMapping
    public R updateById(@RequestBody StgLog stgLog) {
        return R.ok(stgLogService.updateById(stgLog));
    }

    /**
     * 根据id获取
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable String id) {
        return R.ok(stgLogService.getById(id));
    }

    /**
     * 分页获取
     * @param page
     * @param stgLog
     * @return
     */
    @GetMapping("/page")
    public R getRolePage(Page page , StgLog stgLog) {
        return R.ok(stgLogService.page(page, Wrappers.query(stgLog)));
    }
}
