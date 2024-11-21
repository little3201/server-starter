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

package com.server.starter.system.service.impl;

import com.server.starter.system.domain.OperationLog;
import com.server.starter.system.dto.OperationLogDTO;
import com.server.starter.system.repository.OperationLogRepository;
import com.server.starter.system.service.OperationLogService;
import com.server.starter.system.vo.OperationLogVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * operation log service impl.
 *
 * @author wq li
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogRepository operationLogRepository;

    /**
     * <p>Constructor for AccessLogServiceImpl.</p>
     *
     * @param operationLogRepository a {@link OperationLogRepository} object
     */
    public OperationLogServiceImpl(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<OperationLogVO> retrieve(int page, int size, String sortBy, boolean descending, String operation) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return operationLogRepository.findAll(pageable).map(this::convert);
    }

    @Override
    public OperationLogVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");

        return operationLogRepository.findById(id).map(this::convert).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OperationLogVO create(OperationLogDTO dto) {
        OperationLog operationLog = convert(dto, OperationLog.class);

        operationLogRepository.save(operationLog);
        return this.convert(operationLog);
    }

    @Override
    public void remove(Long id) {
        Assert.notNull(id, "id must not be null.");
        operationLogRepository.deleteById(id);
    }

    private OperationLogVO convert(OperationLog operationLog) {
        OperationLogVO vo = convert(operationLog, OperationLogVO.class);
        vo.setIp(Objects.nonNull(operationLog.getIp()) ? operationLog.getIp().toString() : null);
        return vo;
    }
}
