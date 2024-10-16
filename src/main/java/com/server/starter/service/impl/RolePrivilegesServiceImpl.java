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

import com.server.starter.domain.RolePrivileges;
import com.server.starter.repository.RolePrivilegesRepository;
import com.server.starter.service.RolePrivilegesService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * role privileges service impl.
 *
 * @author wq li
 */
@Service
public class RolePrivilegesServiceImpl implements RolePrivilegesService {

    private final RolePrivilegesRepository rolePrivilegesRepository;

    /**
     * <p>Constructor for RolePrivilegesServiceImpl.</p>
     *
     * @param rolePrivilegesRepository a {@link com.server.starter.repository.RolePrivilegesRepository} object
     */
    public RolePrivilegesServiceImpl(RolePrivilegesRepository rolePrivilegesRepository) {
        this.rolePrivilegesRepository = rolePrivilegesRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RolePrivileges> privileges(Long roleId) {
        Assert.notNull(roleId, "roleId must not be null.");

        return rolePrivilegesRepository.findAllByRoleId(roleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RolePrivileges> roles(Long privilegeId) {
        Assert.notNull(privilegeId, "privilegeId must not be null.");

        return rolePrivilegesRepository.findAllByPrivilegeId(privilegeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RolePrivileges> relation(Long roleId, Set<Long> privilegeIds) {
        Assert.notNull(roleId, "roleId must not be null.");
        Assert.notEmpty(privilegeIds, "privilegeIds must not be empty.");

        List<RolePrivileges> rolePrivileges = privilegeIds.stream().map(privilegeId -> {
            RolePrivileges rolePrivilege = new RolePrivileges();
            rolePrivilege.setRoleId(roleId);
            rolePrivilege.setPrivilegeId(privilegeId);
            return rolePrivilege;
        }).toList();
        return rolePrivilegesRepository.saveAll(rolePrivileges);
    }
}
