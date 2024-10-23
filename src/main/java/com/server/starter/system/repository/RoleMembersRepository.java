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

import com.server.starter.system.domain.RoleMembers;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * role members repository.
 *
 * @author wq li
 */
@Repository
public interface RoleMembersRepository extends ListCrudRepository<RoleMembers, Long> {

    /**
     * 根据user主键ID查询
     *
     * @param username 用户名
     * @return 集合
     */
    List<RoleMembers> findAllByUsername(String username);

    /**
     * 根据role查询
     *
     * @param roleId role主键
     * @return 关联数据集
     */
    List<RoleMembers> findAllByRoleId(Long roleId);

}
