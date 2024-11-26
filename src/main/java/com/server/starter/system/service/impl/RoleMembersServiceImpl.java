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

import com.server.starter.system.domain.RoleMembers;
import com.server.starter.system.repository.RoleMembersRepository;
import com.server.starter.system.service.RoleMembersService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * role members service impl.
 *
 * @author wq li
 */
@Service
public class RoleMembersServiceImpl implements RoleMembersService {

    private final RoleMembersRepository roleMembersRepository;

    /**
     * <p>Constructor for RoleMembersServiceImpl.</p>
     *
     * @param roleMembersRepository a {@link RoleMembersRepository} object
     */
    public RoleMembersServiceImpl(RoleMembersRepository roleMembersRepository) {
        this.roleMembersRepository = roleMembersRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleMembers> members(Long roleId) {
        Assert.notNull(roleId, "roleId must not be null.");

        return roleMembersRepository.findAllByRoleId(roleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleMembers> roles(String username) {
        Assert.hasText(username, "username must not be empty.");

        return roleMembersRepository.findAllByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleMembers> relation(Long roleId, Set<String> usernames) {
        Assert.notNull(roleId, "roleId must not be null.");
        Assert.notEmpty(usernames, "usernames must not be empty.");

        List<RoleMembers> roleMembers = usernames.stream().map(username -> {
            RoleMembers roleMember = new RoleMembers();
            roleMember.setRoleId(roleId);
            roleMember.setUsername(username);
            return roleMember;
        }).toList();
        return roleMembersRepository.saveAll(roleMembers);
    }
}
