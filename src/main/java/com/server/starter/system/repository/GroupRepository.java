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

import com.server.starter.system.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * group repository.
 *
 * @author wq li
 */
@Repository
public interface GroupRepository extends ListCrudRepository<Group, Long>,
        PagingAndSortingRepository<Group, Long> {

    /**
     * <p>findAllBySuperiorIdIsNull.</p>
     *
     * @return a {@link Page} object
     */
    Page<Group> findAllBySuperiorIdIsNull(Pageable pageable);

    /**
     * <p>findAllBySuperiorId.</p>
     *
     * @return a {@link Page} object
     */
    Page<Group> findAllBySuperiorId(Long superiorId, Pageable pageable);

    /**
     * 是否存在
     *
     * @param name 名称
     * @return true-存在，false-否
     */
    boolean existsByNameAndIdNot(String name, Long id);

    @Modifying
    @Query("UPDATE groups SET enabled = NOT enabled WHERE id = :id")
    boolean updateEnabledById(Long id);
}
