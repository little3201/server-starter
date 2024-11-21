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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.starter.system.dto.UserDTO;
import com.server.starter.system.service.UserService;
import com.server.starter.system.vo.UserVO;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * user controller test
 *
 * @author wq li
 **/
@WithMockUser
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    private UserVO userVO;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userVO = new UserVO(1L, true, Instant.now());
        userVO.setUsername("test");
        userVO.setEmail("john@test.com");

        userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setEmail("john@test.com");
        userDTO.setAccountNonLocked(true);
        userDTO.setAvatar("steven.jpg");
    }

    @Test
    void retrieve() throws Exception {
        Page<UserVO> voPage = new PageImpl<>(List.of(userVO), Mockito.mock(PageRequest.class), 2L);

        given(this.userService.retrieve(Mockito.anyInt(), Mockito.anyInt(), eq("id"),
                Mockito.anyBoolean(), eq("test"))).willReturn(voPage);

        mvc.perform(get("/users").queryParam("page", "0").queryParam("size", "2")
                        .queryParam("sortBy", "id").queryParam("username", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andDo(print())
                .andReturn();
    }

    @Test
    void fetch() throws Exception {
        given(this.userService.fetch(Mockito.anyLong())).willReturn(userVO);

        mvc.perform(get("/users/{id}", Mockito.anyLong()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"))
                .andDo(print())
                .andReturn();
    }

    @Test
    void fetch_error() throws Exception {
        given(this.userService.fetch(Mockito.anyLong())).willThrow(new RuntimeException());

        mvc.perform(get("/users/{id}", Mockito.anyLong())).andExpect(status().isNoContent())
                .andDo(print()).andReturn();
    }

    @Test
    void create() throws Exception {
        given(this.userService.create(Mockito.any(UserDTO.class))).willReturn(userVO);

        mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)).with(csrf().asHeader()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("test"))
                .andDo(print()).andReturn();
    }

    @Test
    void modify() throws Exception {
        given(this.userService.modify(Mockito.anyLong(), Mockito.any(UserDTO.class))).willReturn(userVO);

        mvc.perform(put("/users/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)).with(csrf().asHeader()))
                .andExpect(status().isAccepted())
                .andDo(print()).andReturn();
    }

    @Test
    void modify_error() throws Exception {
        given(this.userService.modify(Mockito.anyLong(), Mockito.any(UserDTO.class))).willThrow(new RuntimeException());

        mvc.perform(put("/users/{id}", Mockito.anyLong()).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)).with(csrf().asHeader()))
                .andExpect(status().isNotModified())
                .andDo(print()).andReturn();
    }

}