/*
 *  Copyright 2018-2024 little3201.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.server.starter.controller;

import com.server.starter.service.OperationLogService;
import com.server.starter.vo.OperationLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * operation log controller.
 *
 * @author wq li
 */
@RestController
@RequestMapping("/operation-logs")
public class OperationLogController {

    private final Logger logger = LoggerFactory.getLogger(OperationLogController.class);

    private final OperationLogService operationLogService;

    /**
     * <p>Constructor for OperationLogController.</p>
     *
     * @param operationLogService a {@link OperationLogService} object
     */
    public OperationLogController(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    /**
     * 查询
     *
     * @param page       页码
     * @param size       大小
     * @param sortBy     排序字段
     * @param descending 排序方向
     * @return 查询到数据集，异常时返回204
     */
    @GetMapping
    public ResponseEntity<Page<OperationLogVO>> retrieve(@RequestParam int page, @RequestParam int size,
                                                         String sortBy, boolean descending, String name) {
        Page<OperationLogVO> voPage;
        try {
            voPage = operationLogService.retrieve(page, size, sortBy, descending, name);
        } catch (Exception e) {
            logger.error("Retrieve record occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voPage);
    }

    /**
     * 查询信息
     *
     * @param id 主键
     * @return 如果查询到数据，返回查询到的信息，否则返回204状态码
     */
    @GetMapping("/{id}")
    public ResponseEntity<OperationLogVO> fetch(@PathVariable Long id) {
        OperationLogVO vo;
        try {
            vo = operationLogService.fetch(id);
        } catch (Exception e) {
            logger.info("Fetch access log occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vo);
    }

    /**
     * 删除信息
     *
     * @param id 主键
     * @return 如果删除成功，返回200状态码，否则返回417状态码
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        try {
            operationLogService.remove(id);
        } catch (Exception e) {
            logger.error("Remove group occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.ok().build();
    }

}
