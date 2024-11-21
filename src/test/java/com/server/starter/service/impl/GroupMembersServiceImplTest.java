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

import com.server.starter.system.domain.GroupMembers;
import com.server.starter.system.repository.GroupMembersRepository;
import com.server.starter.system.service.impl.GroupMembersServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * group members service test
 *
 * @author wq li
 **/
@ExtendWith(MockitoExtension.class)
class GroupMembersServiceImplTest {

    @Mock
    private GroupMembersRepository groupMembersRepository;

    @InjectMocks
    private GroupMembersServiceImpl groupMembersService;

    @Test
    void members() {
        given(this.groupMembersRepository.findAllByGroupId(Mockito.anyLong())).willReturn(List.of(Mockito.mock(GroupMembers.class)));

        List<GroupMembers> members = groupMembersService.members(1L);
        Assertions.assertNotNull(members);
    }

    @Test
    void groups() {
        given(this.groupMembersRepository.findAllByUsername(Mockito.anyString())).willReturn(List.of(Mockito.mock(GroupMembers.class)));

        List<GroupMembers> groups = groupMembersService.groups("test");
        Assertions.assertNotNull(groups);
    }

    @Test
    void relation() {

        given(this.groupMembersRepository.saveAll(Mockito.anyCollection())).willReturn(Mockito.anyList());

        List<GroupMembers> relation = groupMembersService.relation(1L, Set.of("test"));

        verify(this.groupMembersRepository, times(1)).saveAll(Mockito.anyList());
        Assertions.assertNotNull(relation);
    }
}