/*
 * Copyright (c) 2024.  little3201.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.server.starter.system.service.impl;

import com.server.starter.domain.TreeNode;
import com.server.starter.service.ServletAbstractTreeNodeService;
import com.server.starter.system.domain.Privilege;
import com.server.starter.system.domain.RoleMembers;
import com.server.starter.system.domain.RolePrivileges;
import com.server.starter.system.dto.PrivilegeDTO;
import com.server.starter.system.repository.PrivilegeRepository;
import com.server.starter.system.repository.RoleMembersRepository;
import com.server.starter.system.repository.RolePrivilegesRepository;
import com.server.starter.system.service.PrivilegeService;
import com.server.starter.system.vo.PrivilegeVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * privilege service impl.
 *
 * @author wq li
 */
@Service
public class PrivilegeServiceImpl extends ServletAbstractTreeNodeService<Privilege> implements PrivilegeService {

    public final RoleMembersRepository roleMembersRepository;
    public final RolePrivilegesRepository rolePrivilegesRepository;
    private final PrivilegeRepository privilegeRepository;

    /**
     * Constructor for PrivilegeServiceImpl.
     *
     * @param roleMembersRepository    a {@link RoleMembersRepository} object
     * @param rolePrivilegesRepository a {@link RolePrivilegesRepository} object
     * @param privilegeRepository      a {@link PrivilegeRepository} object
     */
    public PrivilegeServiceImpl(RoleMembersRepository roleMembersRepository, RolePrivilegesRepository rolePrivilegesRepository, PrivilegeRepository privilegeRepository) {
        this.roleMembersRepository = roleMembersRepository;
        this.rolePrivilegesRepository = rolePrivilegesRepository;
        this.privilegeRepository = privilegeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<PrivilegeVO> retrieve(int page, int size, String sortBy, boolean descending, String name) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return privilegeRepository.findAllBySuperiorIdIsNull(pageable)
                .map(privilege -> {
                    PrivilegeVO vo = convertToVO(privilege, PrivilegeVO.class);
                    long count = privilegeRepository.countBySuperiorId(privilege.getId());
                    vo.setCount(count);
                    return vo;
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TreeNode> tree(String username) {
        List<Privilege> privileges = new ArrayList<>();
        List<RoleMembers> roleMembers = roleMembersRepository.findAllByUsername(username);
        if (CollectionUtils.isEmpty(roleMembers)) {
            return Collections.emptyList();
        }
        for (RoleMembers roleMember : roleMembers) {
            List<RolePrivileges> rolePrivileges = rolePrivilegesRepository.findAllByRoleId(roleMember.getRoleId());
            for (RolePrivileges rolePrivilege : rolePrivileges) {
                privilegeRepository.findById(rolePrivilege.getPrivilegeId()).ifPresent(privilege -> {
                    if (privilege.isEnabled()) {
                        privileges.add(privilege);
                    }
                });
            }
        }
        return this.convertTree(privileges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PrivilegeVO> subset(Long superiorId) {
        return privilegeRepository.findAllBySuperiorId(superiorId).stream()
                .map(privilege -> {
                    PrivilegeVO vo = convertToVO(privilege, PrivilegeVO.class);
                    long count = privilegeRepository.countBySuperiorId(privilege.getId());
                    vo.setCount(count);
                    return vo;
                }).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrivilegeVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");

        return privilegeRepository.findById(id)
                .map(privilege -> convertToVO(privilege, PrivilegeVO.class))
                .orElse(null);
    }

    @Override
    public boolean enable(Long id) {
        return privilegeRepository.updateEnabledById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrivilegeVO modify(Long id, PrivilegeDTO dto) {
        Assert.notNull(id, "id must not be null.");
        return privilegeRepository.findById(id).map(existing -> {
                    existing = convert(dto, existing);
                    existing = privilegeRepository.save(existing);
                    return convertToVO(existing, PrivilegeVO.class);
                })
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id) {
        Assert.notNull(id, "id must not be null.");

        privilegeRepository.deleteById(id);
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
        meta.add("actions");
        return convertToTree(privileges, meta);
    }

}
