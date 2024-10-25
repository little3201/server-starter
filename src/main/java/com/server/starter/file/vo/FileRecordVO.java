package com.server.starter.file.vo;

import com.server.starter.file.bo.FileRecordBO;

import java.time.Instant;

/**
 * vo class for file record.
 *
 * @author wq li
 */
public class FileRecordVO extends FileRecordBO {

    private Long id;

    private Instant lastModifiedDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}