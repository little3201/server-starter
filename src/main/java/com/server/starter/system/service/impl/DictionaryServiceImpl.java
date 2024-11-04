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
import com.server.starter.service.ServletAbstractTreeNodeService;
import com.server.starter.system.domain.Dictionary;
import com.server.starter.system.dto.DictionaryDTO;
import com.server.starter.system.repository.DictionaryRepository;
import com.server.starter.system.service.DictionaryService;
import com.server.starter.system.vo.DictionaryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * dictionary service impl.
 *
 * @author wq li
 */
@Service
public class DictionaryServiceImpl extends ServletAbstractTreeNodeService<Dictionary> implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    /**
     * <p>Constructor for DictionaryServiceImpl.</p>
     *
     * @param dictionaryRepository a {@link DictionaryRepository} object
     */
    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<DictionaryVO> retrieve(int page, int size, String sortBy, boolean descending, String name) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return dictionaryRepository.findAllBySuperiorIdIsNull(pageable).map(this::convert);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictionaryVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");

        return dictionaryRepository.findById(id).map(this::convert).orElse(null);
    }

    @Override
    public boolean toggleStatus(Long id) {
        return dictionaryRepository.updateEnabledById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DictionaryVO> subset(Long id) {
        Assert.notNull(id, "id must not be null.");
        return dictionaryRepository.findAllBySuperiorId(id)
                .stream().map(this::convert).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exist(Long superiorId, String name) {
        Assert.hasText(name, "name must not be blank.");
        return dictionaryRepository.existsBySuperiorIdAndName(superiorId, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictionaryVO create(DictionaryDTO dto) {
        Dictionary dictionary = Converter.convert(dto, Dictionary.class);

        dictionaryRepository.save(dictionary);
        return this.convert(dictionary);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictionaryVO modify(Long id, DictionaryDTO dto) {
        Assert.notNull(id, "id must not be null.");
        return dictionaryRepository.findById(id).map(existing -> {
                    Dictionary dictionary = Converter.convert(dto, existing);
                    dictionary = dictionaryRepository.save(dictionary);
                    return this.convert(dictionary);
                })
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id) {
        Assert.notNull(id, "id must not be null.");
        dictionaryRepository.deleteById(id);
    }

    /**
     * 类型转换
     *
     * @param dictionary 信息
     * @return DictionaryVO 输出对象
     */
    private DictionaryVO convert(Dictionary dictionary) {
        return Converter.convert(dictionary, DictionaryVO.class);
    }
}
