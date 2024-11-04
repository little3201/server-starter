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

import com.server.starter.system.domain.Privilege;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * privilege repository.
 *
 * @author wq li
 */
@Mapper
@Repository
public interface PrivilegeRepository extends ListCrudRepository<Privilege, Long>,
        PagingAndSortingRepository<Privilege, Long> {

    /**
     * <p>findAllBySuperiorIdIsNull.</p>
     *
     * @return a {@link Page} object
     */
    Page<Privilege> findAllBySuperiorIdIsNull(Pageable pageable);

    /**
     * <p>findAllBySuperiorId.</p>
     *
     * @param superiorId a {@link java.lang.Long} object
     * @return a {@link java.util.List} object
     */
    List<Privilege> findAllBySuperiorId(Long superiorId);

    /**
     * <p>countBySuperiorId.</p>
     *
     * @param superiorId a {@link java.lang.Long} object
     * @return a long
     */
    long countBySuperiorId(Long superiorId);

}
