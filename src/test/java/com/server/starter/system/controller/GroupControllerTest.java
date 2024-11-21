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
import com.server.starter.system.controller.GroupController;
import com.server.starter.system.dto.GroupDTO;
import com.server.starter.system.service.GroupMembersService;
import com.server.starter.system.service.GroupService;
import com.server.starter.domain.TreeNode;
import com.server.starter.system.vo.GroupVO;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * group controller test
 *
 * @author wq li
 **/
@WithMockUser
@ExtendWith(SpringExtension.class)
@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GroupService groupService;

    @MockBean
    private GroupMembersService groupMembersService;

    private GroupVO groupVO;

    private GroupDTO groupDTO;

    @BeforeEach
    void setUp() {
        groupVO = new GroupVO(1L, true, Instant.now());
        groupVO.setName("test");

        groupDTO = new GroupDTO();
        groupDTO.setName("test");
        groupDTO.setSuperiorId(1L);
        groupDTO.setDescription("description");
    }

    @Test
    void retrieve() throws Exception {
        Page<GroupVO> voPage = new PageImpl<>(List.of(groupVO), Mockito.mock(PageRequest.class), 2L);

        given(this.groupService.retrieve(Mockito.anyInt(), Mockito.anyInt(), eq("id"),
                Mockito.anyBoolean(), Mockito.anyLong(), eq("test"))).willReturn(voPage);

        mvc.perform(get("/groups").queryParam("page", "0").queryParam("size", "2")
                        .queryParam("sortBy", "id").queryParam("superiorId", "1")
                        .queryParam("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andDo(print()).andReturn();
    }

    @Test
    void retrieve_error() throws Exception {
        given(this.groupService.retrieve(Mockito.anyInt(), Mockito.anyInt(), eq("id"), Mockito.anyBoolean(),
                Mockito.anyLong(), Mockito.anyString())).willThrow(new RuntimeException());

        mvc.perform(get("/groups").queryParam("page", "0").queryParam("size", "2")
                        .queryParam("sortBy", "id").queryParam("superiorId", "1")
                        .queryParam("name", "test"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();
    }

    @Test
    void tree() throws Exception {
        TreeNode treeNode = TreeNode.withId(1L).name("test").build();
        given(this.groupService.tree()).willReturn(Collections.singletonList(treeNode));

        mvc.perform(get("/groups/tree")).andExpect(status().isOk())
                .andDo(print()).andReturn();
    }

    @Test
    void fetch() throws Exception {
        given(this.groupService.fetch(Mockito.anyLong())).willReturn(groupVO);

        mvc.perform(get("/groups/{id}", Mockito.anyLong())).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test")).andDo(print()).andReturn();
    }

    @Test
    void fetch_error() throws Exception {
        given(this.groupService.fetch(Mockito.anyLong())).willThrow(new RuntimeException());

        mvc.perform(get("/groups/{id}", Mockito.anyLong())).andExpect(status().isNoContent())
                .andDo(print()).andReturn();
    }

    @Test
    void create() throws Exception {
        given(this.groupService.create(Mockito.any(GroupDTO.class))).willReturn(groupVO);

        mvc.perform(post("/groups").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(groupDTO)).with(csrf().asHeader()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test"))
                .andDo(print()).andReturn();
    }

    @Test
    void create_error() throws Exception {
        given(this.groupService.create(Mockito.any(GroupDTO.class))).willThrow(new RuntimeException());

        mvc.perform(post("/groups").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(groupDTO)).with(csrf().asHeader()))
                .andExpect(status().isExpectationFailed())
                .andDo(print()).andReturn();
    }

    @Test
    void modify() throws Exception {
        given(this.groupService.modify(Mockito.anyLong(), Mockito.any(GroupDTO.class))).willReturn(groupVO);

        mvc.perform(put("/groups/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(groupDTO)).with(csrf().asHeader()))
                .andExpect(status().isAccepted())
                .andDo(print()).andReturn();
    }

    @Test
    void modify_error() throws Exception {
        given(this.groupService.modify(Mockito.anyLong(), Mockito.any(GroupDTO.class))).willThrow(new RuntimeException());

        mvc.perform(put("/groups/{id}", Mockito.anyLong()).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(groupDTO)).with(csrf().asHeader()))
                .andExpect(status().isNotModified())
                .andDo(print()).andReturn();
    }

    @Test
    void remove() throws Exception {
        this.groupService.remove(Mockito.anyLong());

        mvc.perform(delete("/groups/{id}", Mockito.anyLong()).with(csrf().asHeader())).andExpect(status().isOk())
                .andDo(print()).andReturn();
    }

    @Test
    void remove_error() throws Exception {
        doThrow(new RuntimeException()).when(this.groupService).remove(Mockito.anyLong());

        mvc.perform(delete("/groups/{id}", Mockito.anyLong()).with(csrf().asHeader()))
                .andExpect(status().isExpectationFailed())
                .andDo(print()).andReturn();
    }

    @Test
    void members() throws Exception {
        given(this.groupMembersService.members(Mockito.anyLong())).willReturn(Mockito.anyList());

        mvc.perform(get("/groups/{id}/members", 1L)).andExpect(status().isOk())
                .andDo(print()).andReturn();
    }

    @Test
    void members_error() throws Exception {
        doThrow(new RuntimeException()).when(this.groupMembersService).members(Mockito.anyLong());

        mvc.perform(get("/groups/{id}/members", Mockito.anyLong())).andExpect(status().isNoContent())
                .andDo(print()).andReturn();
    }

}