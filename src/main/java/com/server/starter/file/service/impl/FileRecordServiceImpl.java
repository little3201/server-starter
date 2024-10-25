package com.server.starter.file.service.impl;


import com.server.starter.file.domain.FileRecord;
import com.server.starter.file.repository.FileRecordRepository;
import com.server.starter.file.service.FileRecordService;
import com.server.starter.file.vo.FileRecordVO;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * file service impl.
 *
 * @author wq li
 */
@Service
public class FileRecordServiceImpl implements FileRecordService {

    private final FileRecordRepository fileRecordRepository;

    public FileRecordServiceImpl(FileRecordRepository fileRecordRepository) {
        this.fileRecordRepository = fileRecordRepository;
    }

    @Override
    public Page<FileRecordVO> retrieve(int page, int size, String sortBy, boolean descending, String name) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return fileRecordRepository.findAll(pageable).map(this::convert);
    }

    @Override
    public FileRecordVO upload(MultipartFile file) {
        return null;
    }


    /**
     * 数据转换
     *
     * @param fileRecord 数据
     * @return FileVO 输出对象
     */
    private FileRecordVO convert(FileRecord fileRecord) {
        FileRecordVO vo = new FileRecordVO();
        BeanCopier copier = BeanCopier.create(FileRecord.class, FileRecordVO.class, false);
        copier.copy(fileRecord, vo, null);
        return vo;
    }
}
