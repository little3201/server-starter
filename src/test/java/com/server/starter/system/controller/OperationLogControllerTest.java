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

package com.server.starter.system.controller;

import com.server.starter.system.controller.OperationLogController;
import com.server.starter.system.service.OperationLogService;
import com.server.starter.system.vo.OperationLogVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * operation log controller test
 *
 * @author wq li
 **/
@WithMockUser
@ExtendWith(SpringExtension.class)
@WebMvcTest(OperationLogController.class)
class OperationLogControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OperationLogService operationLogService;

    private OperationLogVO operationLogVO;

    @BeforeEach
    void setUp() {
        // vo
        operationLogVO = new OperationLogVO();
        operationLogVO.setIp("12.1.3.2");
        operationLogVO.setLocation("test");
        operationLogVO.setBrowser("Chrome");
        operationLogVO.setDeviceType("PC");
        operationLogVO.setBrowser("Edge");
        operationLogVO.setOs("Mac OS");
        operationLogVO.setReferer("test");
        operationLogVO.setContent("content");
        operationLogVO.setSessionId("sessionId");
        operationLogVO.setStatusCode(200);
        operationLogVO.setOperation("test");
        operationLogVO.setUserAgent("xxx");
    }

    @Test
    void retrieve() throws Exception {
        Page<OperationLogVO> voPage = new PageImpl<>(List.of(operationLogVO), Mockito.mock(PageRequest.class), 2L);

        given(this.operationLogService.retrieve(Mockito.anyInt(), Mockito.anyInt(), eq("id"),
                Mockito.anyBoolean(), eq("test"))).willReturn(voPage);

        mvc.perform(get("/operation-logs").queryParam("page", "0").queryParam("size", "2")
                        .queryParam("sortBy", "id").queryParam("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andDo(print())
                .andReturn();
    }

    @Test
    void retrieve_error() throws Exception {
        given(this.operationLogService.retrieve(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),
                Mockito.anyBoolean(), Mockito.anyString())).willThrow(new RuntimeException());

        mvc.perform(get("/operation-logs").queryParam("page", "0").queryParam("size", "2")
                        .queryParam("sortBy", "id").queryParam("name", "test"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }

}