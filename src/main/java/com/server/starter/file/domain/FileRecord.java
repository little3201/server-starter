package com.server.starter.file.domain;


import com.server.starter.audit.AuditMetadata;
import org.springframework.data.relational.core.mapping.Table;

/**
 * model class for file record.
 *
 * @author wq li
 */
@Table(name = "file_records")
public class FileRecord extends AuditMetadata {

    private String name;

    private String path;

    private String type;

    private float size;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
