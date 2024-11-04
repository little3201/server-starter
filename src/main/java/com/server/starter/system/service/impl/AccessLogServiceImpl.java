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

package com.server.starter.system.service.impl;

import com.server.starter.convert.Converter;
import com.server.starter.system.domain.AccessLog;
import com.server.starter.system.dto.AccessLogDTO;
import com.server.starter.system.repository.AccessLogRepository;
import com.server.starter.system.service.AccessLogService;
import com.server.starter.system.vo.AccessLogVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * access log service impl.
 *
 * @author wq li
 */
@Service
public class AccessLogServiceImpl implements AccessLogService {

    private final AccessLogRepository accessLogRepository;

    /**
     * <p>Constructor for AccessLogServiceImpl.</p>
     *
     * @param accessLogRepository a {@link AccessLogRepository} object
     */
    public AccessLogServiceImpl(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<AccessLogVO> retrieve(int page, int size, String sortBy, boolean descending, String url) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return accessLogRepository.findAll(pageable).map(this::convert);
    }

    @Override
    public AccessLogVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");

        return accessLogRepository.findById(id).map(this::convert).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessLogVO create(AccessLogDTO dto) {
        AccessLog accessLog = Converter.convert(dto, AccessLog.class);

        accessLogRepository.save(accessLog);
        return this.convert(accessLog);
    }

    @Override
    public void remove(Long id) {
        Assert.notNull(id, "id must not be null.");
        accessLogRepository.deleteById(id);
    }

    private AccessLogVO convert(AccessLog accessLog) {
        AccessLogVO vo = Converter.convert(accessLog, AccessLogVO.class);
        vo.setIp(Objects.nonNull(accessLog.getIp()) ? accessLog.getIp().toString() : null);
        vo.setBody(Objects.nonNull(accessLog.getBody()) ? accessLog.getBody().toString() : null);
        return vo;
    }
}
