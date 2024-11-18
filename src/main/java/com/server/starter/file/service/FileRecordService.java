package com.server.starter.file.service;

import com.server.starter.file.dto.FileRecordDTO;
import com.server.starter.file.vo.FileRecordVO;
import com.server.starter.service.ServletBasicService;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

/**
 * file service.
 *
 * @author wq li
 */
public interface FileRecordService extends ServletBasicService<FileRecordDTO, FileRecordVO> {

    /**
     * Retrieves a paginated list of records.
     *
     * @param page       The page number (zero-based).
     * @param size       The number of records per page.
     * @param sortBy     The field to sort by. If null, records are unsorted.
     * @param descending Whether sorting should be in descending order.
     * @param name       The name filter for the records.
     * @return A paginated list of records.
     */
    Page<FileRecordVO> retrieve(int page, int size, String sortBy, boolean descending, String name);

    /**
     * 上传
     *
     * @param file 文件
     * @return 结果
     */
    FileRecordVO upload(MultipartFile file);
}
