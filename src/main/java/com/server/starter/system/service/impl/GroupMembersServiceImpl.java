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

import com.server.starter.system.domain.GroupMembers;
import com.server.starter.system.repository.GroupMembersRepository;
import com.server.starter.system.service.GroupMembersService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * group members service impl.
 *
 * @author wq li
 */
@Service
public class GroupMembersServiceImpl implements GroupMembersService {

    private final GroupMembersRepository groupMembersRepository;

    /**
     * <p>Constructor for GroupMembersServiceImpl.</p>
     *
     * @param groupMembersRepository a {@link GroupMembersRepository} object
     */
    public GroupMembersServiceImpl(GroupMembersRepository groupMembersRepository) {
        this.groupMembersRepository = groupMembersRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupMembers> members(Long groupId) {
        Assert.notNull(groupId, "groupId must not be null.");

        return groupMembersRepository.findAllByGroupId(groupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupMembers> groups(String username) {
        Assert.hasText(username, "username must not be blank.");

        return groupMembersRepository.findAllByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupMembers> relation(Long groupId, Set<String> usernames) {
        Assert.notNull(groupId, "groupId must not be null.");
        Assert.notEmpty(usernames, "usernames must not be empty.");

        List<GroupMembers> groupMembers = usernames.stream().map(username -> {
            GroupMembers groupMember = new GroupMembers();
            groupMember.setGroupId(groupId);
            groupMember.setUsername(username);
            return groupMember;
        }).toList();
        return groupMembersRepository.saveAll(groupMembers);
    }
}
