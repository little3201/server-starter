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
package com.server.starter.system.service.impl;

import com.server.starter.system.domain.Privilege;
import com.server.starter.system.dto.PrivilegeDTO;
import com.server.starter.system.repository.PrivilegeRepository;
import com.server.starter.system.repository.RolePrivilegesRepository;
import com.server.starter.system.service.PrivilegeService;
import com.server.starter.service.ServletAbstractTreeNodeService;
import com.server.starter.domain.TreeNode;
import com.server.starter.system.vo.PrivilegeVO;
import org.springframework.cglib.beans.BeanCopier;
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

    public final RolePrivilegesRepository rolePrivilegesRepository;
    private final PrivilegeRepository privilegeRepository;

    /**
     * <p>Constructor for PrivilegeServiceImpl.</p>
     *
     * @param rolePrivilegesRepository a {@link RolePrivilegesRepository} object
     * @param privilegeRepository      a {@link PrivilegeRepository} object
     */
    public PrivilegeServiceImpl(RolePrivilegesRepository rolePrivilegesRepository, PrivilegeRepository privilegeRepository) {
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

        return privilegeRepository.findAllBySuperiorIdIsNull(pageable).map(this::convert);
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
     * {@inheritDoc}
     */
    @Override
    public List<PrivilegeVO> subset(Long superiorId) {
        return privilegeRepository.findAllBySuperiorId(superiorId).stream().map(this::convert).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrivilegeVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");
        Privilege privilege = privilegeRepository.findById(id).orElse(null);
        if (privilege == null) {
            return null;
        }
        return this.convert(privilege);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrivilegeVO create(PrivilegeDTO dto) {
        Privilege privilege = new Privilege();
        BeanCopier copier = BeanCopier.create(PrivilegeDTO.class, Privilege.class, false);
        copier.copy(dto, privilege, null);

        privilege = privilegeRepository.save(privilege);
        return this.convert(privilege);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrivilegeVO modify(Long id, PrivilegeDTO dto) {
        Assert.notNull(id, "id must not be null.");
        Privilege privilege = privilegeRepository.findById(id).orElse(null);
        if (privilege == null) {
            throw new NoSuchElementException("当前操作数据不存在...");
        }
        BeanCopier copier = BeanCopier.create(PrivilegeDTO.class, Privilege.class, false);
        copier.copy(dto, privilege, null);

        privilege = privilegeRepository.save(privilege);
        return this.convert(privilege);
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
     * 转换对象
     *
     * @param privilege 基础对象
     * @return 结果对象
     */
    private PrivilegeVO convert(Privilege privilege) {
        PrivilegeVO vo = new PrivilegeVO();
        BeanCopier copier = BeanCopier.create(Privilege.class, PrivilegeVO.class, false);
        copier.copy(privilege, vo, null);
        long count = privilegeRepository.countBySuperiorId(privilege.getId());
        vo.setCount(count);
        return vo;
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
