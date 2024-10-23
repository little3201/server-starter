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
package com.server.starter.system.repository;

import com.server.starter.system.domain.GroupMembers;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * group members repository.
 *
 * @author wq li
 */
@Repository
public interface GroupMembersRepository extends ListCrudRepository<GroupMembers, Long> {

    /**
     * 根据group查user
     *
     * @param groupId group主键
     * @return 关联数据集
     */
    List<GroupMembers> findAllByGroupId(Long groupId);

    /**
     * 根据user查group
     *
     * @param username 用户名
     * @return 关联数据集
     */
    List<GroupMembers> findAllByUsername(String username);

}
