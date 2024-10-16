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

import com.server.starter.domain.RolePrivileges;
import com.server.starter.service.PrivilegeService;
import com.server.starter.service.RolePrivilegesService;
import com.server.starter.tree.TreeNode;
import com.server.starter.vo.PrivilegeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * privilege controller.
 *
 * @author wq li
 */
@RestController
@RequestMapping("/privileges")
public class PrivilegeController {

    private final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    private final PrivilegeService privilegeService;
    private final RolePrivilegesService rolePrivilegesService;

    /**
     * <p>Constructor for PrivilegeController.</p>
     *
     * @param privilegeService      a {@link com.server.starter.service.PrivilegeService} object
     * @param rolePrivilegesService a {@link com.server.starter.service.RolePrivilegesService} object
     */
    public PrivilegeController(PrivilegeService privilegeService, RolePrivilegesService rolePrivilegesService) {
        this.privilegeService = privilegeService;
        this.rolePrivilegesService = rolePrivilegesService;
    }

    /**
     * 查询树形数据
     *
     * @return 查询到的数据，否则返回空
     */
    @GetMapping
    public ResponseEntity<List<TreeNode>> tree() {
        List<TreeNode> treeNodes;
        try {
            treeNodes = privilegeService.tree();
        } catch (Exception e) {
            logger.info("Retrieve privilege tree occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(treeNodes);
    }

    /**
     * 查询信息
     *
     * @param id 主键
     * @return 查询到的信息，否则返回204状态码
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrivilegeVO> fetch(@PathVariable Long id) {
        PrivilegeVO vo;
        try {
            vo = privilegeService.fetch(id);
        } catch (Exception e) {
            logger.info("Fetch privilege occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vo);
    }

    /**
     * 查询关联role
     *
     * @param id 主键
     * @return 查询到的数据集，异常时返回204状态码
     */
    @GetMapping("/{id}/roles")
    public ResponseEntity<List<RolePrivileges>> roles(@PathVariable Long id) {
        List<RolePrivileges> voList;
        try {
            voList = rolePrivilegesService.roles(id);
        } catch (Exception e) {
            logger.error("Retrieve privilege roles occurred an error: ", e);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(voList);
    }


}
