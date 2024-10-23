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
package com.server.starter.system.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * model class for role privileges.
 *
 * @author wq li
 */

@Table(name = "role_privileges")
public class RolePrivileges {


    @Id
    private Long id;

    /**
     * role主键
     */
    @Column("role_id")
    private Long roleId;

    /**
     * privilege 主键
     */
    @Column("privilege_id")
    private Long privilegeId;


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
     * <p>Getter for the field <code>roleId</code>.</p>
     *
     * @return a {@link java.lang.Long} object
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * <p>Setter for the field <code>roleId</code>.</p>
     *
     * @param roleId a {@link java.lang.Long} object
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * <p>Getter for the field <code>privilegeId</code>.</p>
     *
     * @return a {@link java.lang.Long} object
     */
    public Long getPrivilegeId() {
        return privilegeId;
    }

    /**
     * <p>Setter for the field <code>privilegeId</code>.</p>
     *
     * @param privilegeId a {@link java.lang.Long} object
     */
    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }
}
