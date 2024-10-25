package com.server.starter.file.repository;


import com.server.starter.file.domain.FileRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * file repository.
 *
 * @author wq li
 */
@Repository
public interface FileRecordRepository extends CrudRepository<FileRecord, Long>,
        PagingAndSortingRepository<FileRecord, Long> {
}
