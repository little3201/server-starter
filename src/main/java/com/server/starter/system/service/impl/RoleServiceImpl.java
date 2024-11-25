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

import com.server.starter.system.domain.Role;
import com.server.starter.system.dto.RoleDTO;
import com.server.starter.system.repository.RoleRepository;
import com.server.starter.system.service.RoleService;
import com.server.starter.system.vo.RoleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * role service impl.
 *
 * @author wq li
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * <p>Constructor for RoleServiceImpl.</p>
     *
     * @param roleRepository a {@link RoleRepository} object
     */
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<RoleVO> retrieve(int page, int size, String sortBy, boolean descending, String name) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return roleRepository.findAll(pageable)
                .map(role -> convert(role, RoleVO.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");

        return roleRepository.findById(id)
                .map(role -> convert(role, RoleVO.class)).orElse(null);
    }

    @Override
    public boolean exists(String name, Long id) {
        Assert.hasText(name, "name must not be blank.");
        if (id == null) {
            return roleRepository.existsByName(name);
        }
        return roleRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public boolean enable(Long id) {
        Assert.notNull(id, "id must not be null.");

        return roleRepository.updateEnabledById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleVO create(RoleDTO dto) {
        Role role = convert(dto, Role.class);

        roleRepository.save(role);
        return convert(role, RoleVO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleVO modify(Long id, RoleDTO dto) {
        Assert.notNull(id, "id must not be null.");
        return roleRepository.findById(id).map(existing -> {
                    Role role = convert(dto, existing);
                    role = roleRepository.save(role);
                    return convert(role, RoleVO.class);
                })
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id) {
        Assert.notNull(id, "id must not be null.");
        roleRepository.deleteById(id);
    }

}
