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

import java.time.Instant;

/**
 * bo class for operation log
 *
 * @author wq li
 */
public abstract class OperationLogBO {

    /**
     * IP地址
     */
    private String ip;

    /**
     * 地理位置
     */
    private String location;

    /**
     * 用户代理信息
     */
    private String userAgent;

    private String content;

    private String operation;

    /**
     * HTTP状态码
     */
    private Integer statusCode;

    /**
     * 响应时间
     */
    private Instant operatedTime;

    /**
     * 来源页面
     */
    private String referer;

    /**
     * 会话标识符
     */
    private String sessionId;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * <p>Getter for the field <code>ip</code>.</p>
     *
     * @return a {@link String} object
     */
    public String getIp() {
        return ip;
    }

    /**
     * <p>Setter for the field <code>ip</code>.</p>
     *
     * @param ip a {@link String} object
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * <p>Getter for the field <code>location</code>.</p>
     *
     * @return a {@link String} object
     */
    public String getLocation() {
        return location;
    }

    /**
     * <p>Setter for the field <code>location</code>.</p>
     *
     * @param location a {@link String} object
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * <p>Getter for the field <code>userAgent</code>.</p>
     *
     * @return a {@link String} object
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * <p>Setter for the field <code>userAgent</code>.</p>
     *
     * @param userAgent a {@link String} object
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * <p>Getter for the field <code>statusCode</code>.</p>
     *
     * @return a {@link Integer} object
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * <p>Setter for the field <code>statusCode</code>.</p>
     *
     * @param statusCode a {@link Integer} object
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * <p>Getter for the field <code>operatedTime</code>.</p>
     *
     * @return a {@link Instant} object
     */
    public Instant getOperatedTime() {
        return operatedTime;
    }

    /**
     * <p>Setter for the field <code>operatedTime</code>.</p>
     *
     * @param operatedTime a {@link Instant} object
     */
    public void setOperatedTime(Instant operatedTime) {
        this.operatedTime = operatedTime;
    }

    /**
     * <p>Getter for the field <code>referer</code>.</p>
     *
     * @return a {@link String} object
     */
    public String getReferer() {
        return referer;
    }

    /**
     * <p>Setter for the field <code>referer</code>.</p>
     *
     * @param referer a {@link String} object
     */
    public void setReferer(String referer) {
        this.referer = referer;
    }

    /**
     * <p>Getter for the field <code>sessionId</code>.</p>
     *
     * @return a {@link String} object
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * <p>Setter for the field <code>sessionId</code>.</p>
     *
     * @param sessionId a {@link String} object
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * <p>Getter for the field <code>deviceType</code>.</p>
     *
     * @return a {@link String} object
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * <p>Setter for the field <code>deviceType</code>.</p>
     *
     * @param deviceType a {@link String} object
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * <p>Getter for the field <code>os</code>.</p>
     *
     * @return a {@link String} object
     */
    public String getOs() {
        return os;
    }

    /**
     * <p>Setter for the field <code>os</code>.</p>
     *
     * @param os a {@link String} object
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     * <p>Getter for the field <code>browser</code>.</p>
     *
     * @return a {@link String} object
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * <p>Setter for the field <code>browser</code>.</p>
     *
     * @param browser a {@link String} object
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }
}
