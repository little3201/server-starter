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
package com.server.starter.service.impl;

import com.server.starter.domain.Privilege;
import com.server.starter.repository.PrivilegeRepository;
import com.server.starter.repository.RolePrivilegesRepository;
import com.server.starter.service.PrivilegeService;
import com.server.starter.service.ServletAbstractTreeNodeService;
import com.server.starter.tree.TreeNode;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * privilege service impl.
 *
 * @author wq li
 */
@Service
public class PrivilegeServiceImpl extends ServletAbstractTreeNodeService<Privilege> implements PrivilegeService {

    public final RolePrivilegesRepository rolePrivilegesRepository;
    private final PrivilegeRepository privilegeRepository;

    /**
     * <p>Constructor for PrivilegeServiceImpl.</p>
     *
     * @param rolePrivilegesRepository a {@link com.server.starter.repository.RolePrivilegesRepository} object
     * @param privilegeRepository      a {@link com.server.starter.repository.PrivilegeRepository} object
     */
    public PrivilegeServiceImpl(RolePrivilegesRepository rolePrivilegesRepository, PrivilegeRepository privilegeRepository) {
        this.rolePrivilegesRepository = rolePrivilegesRepository;
        this.privilegeRepository = privilegeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TreeNode> tree() {
        List<Privilege> privileges = privilegeRepository.findAll();
        return this.convertTree(privileges);
    }

    /**
     * 转换为TreeNode
     *
     * @param privileges 集合数据
     * @return 树集合
     */
    private List<TreeNode> convertTree(List<Privilege> privileges) {
        if (CollectionUtils.isEmpty(privileges)) {
            return Collections.emptyList();
        }
        Set<String> meta = new HashSet<>();
        meta.add("path");
        meta.add("redirect");
        meta.add("component");
        meta.add("icon");
        meta.add("hidden");
        return this.convert(privileges, meta);
    }

}
