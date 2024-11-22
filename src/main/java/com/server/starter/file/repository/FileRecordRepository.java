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

package com.server.starter.file.repository;


import com.server.starter.file.domain.FileRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRecordRepository extends CrudRepository<FileRecord, Long>,
        PagingAndSortingRepository<FileRecord, Long> {

    /**
     * Finds all records with name containing the specified string.
     *
     * @param name     The name filter for the templates.
     * @param pageable The pagination information.
     * @return A paginated list of templates.
     */
    Page<FileRecord> findAllByNameContaining(String name, Pageable pageable);
}
