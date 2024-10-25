package com.server.starter.file.vo;

import com.server.starter.file.bo.FileRecordBO;

/**
 * vo class for file record.
 *
 * @author wq li
 */
public class FileRecordVO extends FileRecordBO {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
