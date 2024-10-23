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

package com.server.starter.service.impl;

import com.server.starter.system.domain.Dictionary;
import com.server.starter.system.dto.DictionaryDTO;
import com.server.starter.system.repository.DictionaryRepository;
import com.server.starter.system.service.impl.DictionaryServiceImpl;
import com.server.starter.system.vo.DictionaryVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * dictionary controller test
 *
 * @author wq li
 **/
@ExtendWith(MockitoExtension.class)
class DictionaryServiceImplTest {


    @Mock
    private DictionaryRepository dictionaryRepository;

    @InjectMocks
    private DictionaryServiceImpl dictionaryService;

    private DictionaryDTO dictionaryDTO;

    @BeforeEach
    void setUp() {
        dictionaryDTO = new DictionaryDTO();
        dictionaryDTO.setName("group");
    }

    @Test
    void retrieve() {
        Page<Dictionary> page = new PageImpl<>(List.of(Mockito.mock(Dictionary.class)));

        given(this.dictionaryRepository.findAllBySuperiorIdIsNull(Mockito.any(Pageable.class))).willReturn(page);

        Page<DictionaryVO> voPage = dictionaryService.retrieve(0, 2, "id", true, "test");

        Assertions.assertNotNull(voPage.getContent());
    }

    @Test
    void fetch() {
        given(this.dictionaryRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(Mockito.mock(Dictionary.class)));

        DictionaryVO dictionaryVO = dictionaryService.fetch(1L);

        Assertions.assertNotNull(dictionaryVO);
    }

    @Test
    void subset() {
        given(this.dictionaryRepository.findAllBySuperiorId(Mockito.anyLong())).willReturn(List.of(Mockito.mock(Dictionary.class)));

        List<DictionaryVO> dictionaryVOS = dictionaryService.subset(1L);

        Assertions.assertNotNull(dictionaryVOS);
    }

    @Test
    void lower_empty() {
        given(this.dictionaryRepository.findAllBySuperiorId(Mockito.anyLong())).willReturn(Collections.emptyList());

        List<DictionaryVO> dictionaryVOS = dictionaryService.subset(1L);

        Assertions.assertEquals(Collections.emptyList(), dictionaryVOS);
    }

    @Test
    void exist() {
        given(this.dictionaryRepository.existsByName(Mockito.anyString())).willReturn(true);

        boolean exist = dictionaryService.exist("性别");

        Assertions.assertTrue(exist);
    }

    @Test
    void create() {
        given(this.dictionaryRepository.save(Mockito.any(Dictionary.class))).willReturn(Mockito.mock(Dictionary.class));

        DictionaryVO dictionaryVO = dictionaryService.create(Mockito.mock(DictionaryDTO.class));

        verify(this.dictionaryRepository, times(1)).save(Mockito.any(Dictionary.class));
        Assertions.assertNotNull(dictionaryVO);
    }

    @Test
    void modify() {
        given(this.dictionaryRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(Mockito.mock(Dictionary.class)));

        given(this.dictionaryRepository.save(Mockito.any(Dictionary.class))).willReturn(Mockito.mock(Dictionary.class));

        DictionaryVO dictionaryVO = dictionaryService.modify(1L, dictionaryDTO);

        verify(this.dictionaryRepository, times(1)).save(Mockito.any(Dictionary.class));
        Assertions.assertNotNull(dictionaryVO);
    }

    @Test
    void remove() {
        dictionaryService.remove(1L);

        verify(this.dictionaryRepository, times(1)).deleteById(Mockito.anyLong());
    }
}