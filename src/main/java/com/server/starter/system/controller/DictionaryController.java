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

package com.server.starter.system.controller;

import com.server.starter.system.dto.DictionaryDTO;
import com.server.starter.system.service.DictionaryService;
import com.server.starter.system.vo.DictionaryVO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * dictionary controller.
 *
 * @author wq li
 */
@RestController
@RequestMapping("/dictionaries")
public class DictionaryController {

    private final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    private final DictionaryService dictionaryService;

    /**
     * <p>Constructor for DictionaryController.</p>
     *
     * @param dictionaryService a {@link DictionaryService} object
     */
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * 分页查询
     *
     * @param page       页码
     * @param size       大小
     * @param sortBy     排序字段
     * @param descending 排序方向
     * @return 查询的数据集，异常时返回204状态码
     */
    @GetMapping
    public ResponseEntity<Page<DictionaryVO>> retrieve(@RequestParam int page, @RequestParam int size,
                                                       String sortBy, boolean descending, String name) {
        Page<DictionaryVO> voPage;
        try {
            voPage = dictionaryService.retrieve(page, size, sortBy, descending, name);
        } catch (Exception e) {
            logger.error("Retrieve dictionary occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voPage);
    }

    /**
     * 查询下级数据
     *
     * @param id a {@link java.lang.Long} object
     * @return 查询到的数据，否则返回空
     */
    @GetMapping("/{id}/subset")
    public ResponseEntity<List<DictionaryVO>> subset(@PathVariable Long id) {
        List<DictionaryVO> voList;
        try {
            voList = dictionaryService.subset(id);
        } catch (Exception e) {
            logger.info("Retrieve dictionary subset occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据 id 查询
     *
     * @param id 业务id
     * @return 查询的数据，异常时返回204状态码
     */
    @GetMapping("/{id}")
    public ResponseEntity<DictionaryVO> fetch(@PathVariable Long id) {
        DictionaryVO vo;
        try {
            vo = dictionaryService.fetch(id);
        } catch (Exception e) {
            logger.error("Fetch dictionary occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vo);
    }

    /**
     * 是否存在
     *
     * @param name 名称
     * @return 如果查询到数据，返回查询到的信息，否则返回204状态码
     */
    @GetMapping("/{name}/exist")
    public ResponseEntity<Boolean> exist(@PathVariable String name) {
        boolean exist;
        try {
            exist = dictionaryService.exist(name);
        } catch (Exception e) {
            logger.info("Query dictionary exist occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exist);
    }

    /**
     * 添加信息
     *
     * @param dto 要添加的数据
     * @return 如果添加数据成功，返回添加后的信息，否则返回417状态码
     */
    @PostMapping
    public ResponseEntity<DictionaryVO> create(@RequestBody @Valid DictionaryDTO dto) {
        DictionaryVO vo;
        try {
            vo = dictionaryService.create(dto);
        } catch (Exception e) {
            logger.error("Create dictionary occurred an error: ", e);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(vo);
    }

    /**
     * 修改信息
     *
     * @param dto 要添加的数据
     * @param id  a {@link java.lang.Long} object
     * @return 如果添加数据成功，返回添加后的信息，否则返回417状态码
     */
    @PutMapping("/{id}")
    public ResponseEntity<DictionaryVO> modify(@PathVariable Long id, @RequestBody @Valid DictionaryDTO dto) {
        DictionaryVO vo;
        try {
            vo = dictionaryService.modify(id, dto);
        } catch (Exception e) {
            logger.error("Modify dictionary occurred an error: ", e);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return ResponseEntity.accepted().body(vo);
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
            dictionaryService.remove(id);
        } catch (Exception e) {
            logger.error("Remove group occurred an error: ", e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.ok().build();
    }
}
