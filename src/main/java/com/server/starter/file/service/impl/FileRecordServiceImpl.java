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

package com.server.starter.file.service.impl;


import com.server.starter.file.repository.FileRecordRepository;
import com.server.starter.file.service.FileRecordService;
import com.server.starter.file.vo.FileRecordVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = pageable(page, size, sortBy, descending);
        if (StringUtils.hasText(name)) {
            return fileRecordRepository.findAllByNameContaining(name, pageable)
                    .map(fileRecord -> convertToVO(fileRecord, FileRecordVO.class));
        }
        return fileRecordRepository.findAll(pageable)
                .map(fileRecord -> convertToVO(fileRecord, FileRecordVO.class));
    }

    @Override
    public FileRecordVO upload(MultipartFile file) {
        return null;
    }

}
