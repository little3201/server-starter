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

package com.server.starter.controller;

import com.server.starter.system.controller.AccessLogController;
import com.server.starter.system.service.AccessLogService;
import com.server.starter.system.vo.AccessLogVO;
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
 * access log controller test
 *
 * @author wq li
 **/
@WithMockUser
@ExtendWith(SpringExtension.class)
@WebMvcTest(AccessLogController.class)
class AccessLogControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccessLogService accessLogService;

    private AccessLogVO accessLogVO;

    @BeforeEach
    void setUp() {
        // vo
        accessLogVO = new AccessLogVO();
        accessLogVO.setIp("12.1.3.2");
        accessLogVO.setLocation("test");
        accessLogVO.setHttpMethod("POST");
        accessLogVO.setResponseTimes(232L);
        accessLogVO.setResponseMessage("sessionId");
        accessLogVO.setStatusCode(200);
        accessLogVO.setUrl("/test");
        accessLogVO.setParams("xxx");
    }

    @Test
    void retrieve() throws Exception {
        Page<AccessLogVO> voPage = new PageImpl<>(List.of(accessLogVO), Mockito.mock(PageRequest.class), 2L);

        given(this.accessLogService.retrieve(Mockito.anyInt(), Mockito.anyInt(), eq("id"),
                Mockito.anyBoolean(), eq("test"))).willReturn(voPage);

        mvc.perform(get("/access-logs").queryParam("page", "0").queryParam("size", "2")
                        .queryParam("sortBy", "id").queryParam("url", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andDo(print())
                .andReturn();
    }

    @Test
    void retrieve_error() throws Exception {
        given(this.accessLogService.retrieve(Mockito.anyInt(), Mockito.anyInt(), eq("id"),
                Mockito.anyBoolean(), eq("test"))).willThrow(new RuntimeException());

        mvc.perform(get("/access-logs").queryParam("page", "0").queryParam("size", "2")
                        .queryParam("sortBy", "id").queryParam("url", "test"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }

}