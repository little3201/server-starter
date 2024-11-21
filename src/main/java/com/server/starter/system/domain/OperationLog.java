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


import com.server.starter.audit.AuditMetadata;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

/**
 * model class for operation log.
 *
 * @author wq li
 */

@Table(name = "operation_logs")
public class OperationLog extends AuditMetadata {

    private String operation;

    private Object ip;

    private String location;

    private String content;

    @Column("user_agent")
    private String userAgent;

    @Column("status_code")
    private Integer statusCode;

    @Column("operated_time")
    private Instant operatedTime;

    private String referer;

    @Column("session_id")
    private String sessionId;

    @Column("device_type")
    private String deviceType;

    private String os;

    private String browser;


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * <p>Getter for the field <code>ip</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public Object getIp() {
        return ip;
    }

    /**
     * <p>Setter for the field <code>ip</code>.</p>
     *
     * @param ip a {@link java.lang.String} object
     */
    public void setIp(Object ip) {
        this.ip = ip;
    }

    /**
     * <p>Getter for the field <code>location</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getLocation() {
        return location;
    }

    /**
     * <p>Setter for the field <code>location</code>.</p>
     *
     * @param location a {@link java.lang.String} object
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * <p>Getter for the field <code>content</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getContent() {
        return content;
    }

    /**
     * <p>Setter for the field <code>content</code>.</p>
     *
     * @param content a {@link java.lang.String} object
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * <p>Getter for the field <code>userAgent</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * <p>Setter for the field <code>userAgent</code>.</p>
     *
     * @param userAgent a {@link java.lang.String} object
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * <p>Getter for the field <code>statusCode</code>.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * <p>Setter for the field <code>statusCode</code>.</p>
     *
     * @param statusCode a {@link java.lang.Integer} object
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * <p>Getter for the field <code>operatedTime</code>.</p>
     *
     * @return a {@link java.time.Instant} object
     */
    public Instant getOperatedTime() {
        return operatedTime;
    }

    /**
     * <p>Setter for the field <code>operatedTime</code>.</p>
     *
     * @param operatedTime a {@link java.time.Instant} object
     */
    public void setOperatedTime(Instant operatedTime) {
        this.operatedTime = operatedTime;
    }

    /**
     * <p>Getter for the field <code>referer</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getReferer() {
        return referer;
    }

    /**
     * <p>Setter for the field <code>referer</code>.</p>
     *
     * @param referer a {@link java.lang.String} object
     */
    public void setReferer(String referer) {
        this.referer = referer;
    }

    /**
     * <p>Getter for the field <code>sessionId</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * <p>Setter for the field <code>sessionId</code>.</p>
     *
     * @param sessionId a {@link java.lang.String} object
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * <p>Getter for the field <code>deviceType</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * <p>Setter for the field <code>deviceType</code>.</p>
     *
     * @param deviceType a {@link java.lang.String} object
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * <p>Getter for the field <code>os</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getOs() {
        return os;
    }

    /**
     * <p>Setter for the field <code>os</code>.</p>
     *
     * @param os a {@link java.lang.String} object
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * <p>Getter for the field <code>browser</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * <p>Setter for the field <code>browser</code>.</p>
     *
     * @param browser a {@link java.lang.String} object
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
