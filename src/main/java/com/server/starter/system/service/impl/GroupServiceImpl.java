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

import com.server.starter.convert.Converter;
import com.server.starter.domain.TreeNode;
import com.server.starter.service.ServletAbstractTreeNodeService;
import com.server.starter.system.domain.Group;
import com.server.starter.system.dto.GroupDTO;
import com.server.starter.system.repository.GroupRepository;
import com.server.starter.system.service.GroupService;
import com.server.starter.system.vo.GroupVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * group service impl.
 *
 * @author wq li
 */
@Service
public class GroupServiceImpl extends ServletAbstractTreeNodeService<Group> implements GroupService {

    private final GroupRepository groupRepository;

    /**
     * <p>Constructor for GroupServiceImpl.</p>
     *
     * @param groupRepository a {@link GroupRepository} object
     */
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<GroupVO> retrieve(int page, int size, String sortBy, boolean descending, Long superiorId, String name) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        if (superiorId == null) {
            return groupRepository.findAllBySuperiorIdIsNull(pageable).map(this::convert);
        }
        return groupRepository.findAllBySuperiorId(superiorId, pageable).map(this::convert);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TreeNode> tree() {
        List<Group> groups = groupRepository.findAll();
        return this.convertTree(groups);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");

        return groupRepository.findById(id).map(this::convert).orElse(null);
    }

    @Override
    public boolean exists(String name, Long id) {
        if (id == null) {
            return groupRepository.existsByName(name);
        }
        return groupRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public boolean toggleStatus(Long id) {
        return groupRepository.updateEnabledById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupVO create(GroupDTO dto) {
        Group group = Converter.convert(dto, Group.class);

        groupRepository.save(group);
        return this.convert(group);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupVO modify(Long id, GroupDTO dto) {
        Assert.notNull(id, "id must not be null.");
        return groupRepository.findById(id).map(existing -> {
                    Group group = Converter.convert(dto, existing);
                    group = groupRepository.save(group);
                    return this.convert(group);
                })
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id) {
        Assert.notNull(id, "id must not be null.");
        groupRepository.deleteById(id);
    }

    /**
     * 转换对象
     *
     * @param group 基础对象
     * @return 结果对象
     */
    private GroupVO convert(Group group) {
        return Converter.convert(group, GroupVO.class);
    }

    /**
     * 转换为TreeNode
     *
     * @param groups 集合数据
     * @return 树集合
     */
    private List<TreeNode> convertTree(List<Group> groups) {
        if (CollectionUtils.isEmpty(groups)) {
            return Collections.emptyList();
        }
        return this.convert(groups, null);
    }

}
