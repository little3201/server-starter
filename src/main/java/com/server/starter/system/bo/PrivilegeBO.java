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

package com.server.starter.system.bo;


import jakarta.validation.constraints.NotBlank;

import java.util.Set;

/**
 * bo class for privilege
 *
 * @author wq li
 */
public abstract class PrivilegeBO {


    @NotBlank
    private String name;

    private Long superiorId;

    private String path;

    /**
     * 跳转路径
     */
    private String redirect;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    @NotBlank(message = "icon must not be blank.")
    private String icon;

    private Set<String> actions;

    /**
     * 描述
     */
    private String description;

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>superiorId</code>.</p>
     *
     * @return a {@link java.lang.Long} object
     */
    public Long getSuperiorId() {
        return superiorId;
    }

    /**
     * <p>Setter for the field <code>superiorId</code>.</p>
     *
     * @param superiorId a {@link java.lang.String} object
     */
    public void setSuperiorId(Long superiorId) {
        this.superiorId = superiorId;
    }

    /**
     * <p>Getter for the field <code>path</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getPath() {
        return path;
    }

    /**
     * <p>Setter for the field <code>path</code>.</p>
     *
     * @param path a {@link java.lang.String} object
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * <p>Getter for the field <code>redirect</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getRedirect() {
        return redirect;
    }

    /**
     * <p>Setter for the field <code>redirect</code>.</p>
     *
     * @param redirect a {@link java.lang.String} object
     */
    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    /**
     * <p>Getter for the field <code>component</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getComponent() {
        return component;
    }

    /**
     * <p>Setter for the field <code>component</code>.</p>
     *
     * @param component a {@link java.lang.String} object
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * <p>Getter for the field <code>icon</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getIcon() {
        return icon;
    }

    /**
     * <p>Setter for the field <code>icon</code>.</p>
     *
     * @param icon a {@link java.lang.String} object
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public Set<String> getActions() {
        return actions;
    }

    /**
     * <p>Setter for the field <code>actions</code>.</p>
     *
     * @param actions a {@link java.lang.String} object
     */
    public void setActions(Set<String> actions) {
        this.actions = actions;
    }

    /**
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Setter for the field <code>description</code>.</p>
     *
     * @param description a {@link java.lang.String} object
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
