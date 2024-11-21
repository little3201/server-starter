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
package com.server.starter.system.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * model class for group roles.
 *
 * @author wq li
 */

@Table(name = "group_authorities")
public class GroupAuthorities {


    @Id
    private Long id;

    /**
     * group主键
     */
    @Column("group_id")
    private Long groupId;

    /**
     * role主键
     */
    private String authority;


    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a {@link java.lang.Long} object
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id a {@link java.lang.Long} object
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>groupId</code>.</p>
     *
     * @return a {@link java.lang.Long} object
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * <p>Setter for the field <code>groupId</code>.</p>
     *
     * @param groupId a {@link java.lang.Long} object
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }


    /**
     * <p>Getter for the field <code>authority</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * <p>Setter for the field <code>authority</code>.</p>
     *
     * @param authority a {@link java.lang.String} object
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
