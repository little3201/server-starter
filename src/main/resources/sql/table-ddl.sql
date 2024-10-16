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

-- Drop table if exists groups
DROP TABLE IF EXISTS groups;

-- Create table groups
CREATE TABLE groups
(
    id                 bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    group_name         varchar(50) NOT NULL,
    superior_id        bigint,
    enabled            boolean     NOT NULL DEFAULT true,
    created_by         varchar(50),
    created_date       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   varchar(50),
    last_modified_date timestamp
);

-- Add comment to the table and columns
COMMENT
ON TABLE groups IS '用户组表';
COMMENT
ON COLUMN groups.id IS '主键';
COMMENT
ON COLUMN groups.group_name IS '名称';
COMMENT
ON COLUMN groups.enabled IS '是否启用';
COMMENT
ON COLUMN groups.created_by IS '创建者';
COMMENT
ON COLUMN groups.created_date IS '创建时间';
COMMENT
ON COLUMN groups.last_modified_by IS '最后修改者';
COMMENT
ON COLUMN groups.last_modified_date IS '最后修改时间';

-- Drop table if exists users
DROP TABLE IF EXISTS users;

-- Create table users
CREATE TABLE users
(
    id                     bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username               varchar(50) UNIQUE NOT NULL,
    full_name              varchar(50),
    password               varchar(100)       NOT NULL,
    email                  varchar(50),
    enabled                boolean            NOT NULL DEFAULT true,
    account_non_locked     boolean,
    account_expires_at     timestamp,
    credentials_expires_at timestamp,
    created_by             varchar(50),
    created_date           timestamp          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by       varchar(50),
    last_modified_date     timestamp
);

-- Add comment to the table and columns
COMMENT
ON TABLE users IS '用户表';
COMMENT
ON COLUMN users.id IS '主键';
COMMENT
ON COLUMN users.username IS '用户名';
COMMENT
ON COLUMN users.password IS '密码';
COMMENT
ON COLUMN users.email IS '邮箱';
COMMENT
ON COLUMN users.avatar IS '头像';
COMMENT
ON COLUMN users.enabled IS '是否启用';
COMMENT
ON COLUMN users.account_non_locked IS '是否未锁定';
COMMENT
ON COLUMN users.account_expires_at IS '失效时间';
COMMENT
ON COLUMN users.credentials_expires_at IS '密码失效时间';
COMMENT
ON COLUMN users.created_by IS '创建者';
COMMENT
ON COLUMN users.created_date IS '创建时间';
COMMENT
ON COLUMN users.last_modified_by IS '最后修改者';
COMMENT
ON COLUMN users.last_modified_date IS '最后修改时间';

-- Drop table if exists authorities
DROP TABLE IF EXISTS authorities;

-- Create table authorities
CREATE TABLE authorities
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username  varchar(50) not null,
    authority varchar(50) not null,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) references users (username)
);

-- Add comment to the table and columns
COMMENT
ON TABLE authorities IS '用户权限表';
COMMENT
ON COLUMN authorities.id IS '主键';
COMMENT
ON COLUMN authorities.username IS '用户名';
COMMENT
ON COLUMN authorities.authority IS '权限';

-- Create unique index
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

-- Drop table if exists roles
DROP TABLE IF EXISTS roles;

-- Create table roles
CREATE TABLE roles
(
    id                 bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name               varchar(50) NOT NULL,
    description        varchar(255),
    enabled            boolean     NOT NULL DEFAULT true,
    created_by         varchar(50),
    created_date       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   varchar(50),
    last_modified_date timestamp
);

-- Add comment to the table and columns
COMMENT
ON TABLE roles IS '角色表';
COMMENT
ON COLUMN roles.id IS '主键';
COMMENT
ON COLUMN roles.name IS '名称';
COMMENT
ON COLUMN roles.description IS '描述';
COMMENT
ON COLUMN roles.enabled IS '是否启用';
COMMENT
ON COLUMN roles.created_by IS '创建者';
COMMENT
ON COLUMN roles.created_date IS '创建时间';
COMMENT
ON COLUMN roles.last_modified_by IS '最后修改者';
COMMENT
ON COLUMN roles.last_modified_date IS '最后修改时间';

-- Drop table if exists group_members
DROP TABLE IF EXISTS group_members;

-- Create table group_members
CREATE TABLE group_members
(
    id       bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    group_id bigint      NOT NULL,
    username varchar(50) NOT NULL,
    CONSTRAINT fk_group_members_group foreign key (group_id) references groups (id)
);

-- Add comment to the table and columns
COMMENT
ON TABLE group_members IS '用户组成员关系表';
COMMENT
ON COLUMN group_members.id IS '主键';
COMMENT
ON COLUMN group_members.group_id IS '用户组ID';
COMMENT
ON COLUMN group_members.username IS '用户名';

-- Drop table if exists group_authorities
DROP TABLE IF EXISTS group_authorities;

-- Create table group_authorities
CREATE TABLE group_authorities
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    group_id  bigint      NOT NULL,
    authority varchar(50) NOT NULL,
    CONSTRAINT fk_group_authorities_group FOREIGN KEY (group_id) references groups (id)
);

-- Add comment to the table and columns
COMMENT
ON TABLE group_authorities IS '用户组权限关系表';
COMMENT
ON COLUMN group_authorities.id IS '主键';
COMMENT
ON COLUMN group_authorities.group_id IS '用户组ID';
COMMENT
ON COLUMN group_authorities.authority IS '权限';

-- Drop table if exists persistent_logins
DROP TABLE IF EXISTS persistent_logins;

-- Create table persistent_logins
CREATE TABLE persistent_logins
(
    series    varchar(64) primary key,
    username  varchar(50) not null,
    token     varchar(64) not null,
    last_used timestamp   not null
);

-- Add comment to the table and columns
COMMENT
ON TABLE persistent_logins IS '持久化登录表';
COMMENT
ON COLUMN persistent_logins.username IS '用户名';
COMMENT
ON COLUMN persistent_logins.series IS '系列';
COMMENT
ON COLUMN persistent_logins.token IS '令牌';
COMMENT
ON COLUMN persistent_logins.last_used IS '最后使用时间';

-- Drop table if exists role_members
DROP TABLE IF EXISTS role_members;

-- Create table role_members
CREATE TABLE role_members
(
    id       bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_id  bigint      NOT NULL,
    username varchar(50) NOT NULL,
    CONSTRAINT fk_role_members_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Add comment to the table and columns
COMMENT
ON TABLE role_members IS '角色成员关系表';
COMMENT
ON COLUMN role_members.id IS '主键';
COMMENT
ON COLUMN role_members.role_id IS '角色ID';
COMMENT
ON COLUMN role_members.username IS '用户名';

-- Drop table if exists privileges
DROP TABLE IF EXISTS privileges;

-- Create table privileges
CREATE TABLE privileges
(
    id                 bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    superior_id        bigint,
    name               varchar(50) NOT NULL,
    path               varchar(127),
    redirect           varchar(255),
    component          varchar(255),
    icon               varchar(127),
    description        varchar(255),
    enabled            boolean     NOT NULL DEFAULT true,
    created_by         varchar(50),
    created_date       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   varchar(50),
    last_modified_date timestamp
);

-- Add comment to the table and columns
COMMENT
ON TABLE privileges IS '权限表';
COMMENT
ON COLUMN privileges.id IS '主键';
COMMENT
ON COLUMN privileges.superior_id IS '上级ID';
COMMENT
ON COLUMN privileges.name IS '名称';
COMMENT
ON COLUMN privileges.path IS '路径';
COMMENT
ON COLUMN privileges.redirect IS '跳转路径';
COMMENT
ON COLUMN privileges.component IS '组件路径';
COMMENT
ON COLUMN privileges.icon IS '图标';
COMMENT
ON COLUMN privileges.description IS '描述';
COMMENT
ON COLUMN privileges.enabled IS '是否启用';
COMMENT
ON COLUMN privileges.created_by IS '创建者';
COMMENT
ON COLUMN privileges.created_date IS '创建时间';
COMMENT
ON COLUMN privileges.last_modified_by IS '最后修改者';
COMMENT
ON COLUMN privileges.last_modified_date IS '最后修改时间';

-- Drop table if exists role_privileges
DROP TABLE IF EXISTS role_privileges;

-- Create table role_privileges
CREATE TABLE role_privileges
(
    id           bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_id      bigint NOT NULL,
    privilege_id bigint NOT NULL,
    CONSTRAINT fk_role_privileges_role FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT fk_role_privileges_privilege FOREIGN KEY (privilege_id) REFERENCES privileges (id)
);

-- Add comment to the table and columns
COMMENT
ON TABLE role_privileges IS '角色权限关系表';
COMMENT
ON COLUMN role_privileges.id IS '主键';
COMMENT
ON COLUMN role_privileges.role_id IS '角色ID';
COMMENT
ON COLUMN role_privileges.privilege_id IS '权限ID';

-- Drop table if exists dictionaries
DROP TABLE IF EXISTS dictionaries;

-- Create table dictionaries
CREATE TABLE dictionaries
(
    id                 bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name               varchar(50) NOT NULL,
    superior_id        bigint,
    description        varchar(255),
    enabled            boolean     NOT NULL DEFAULT true,
    created_by         varchar(50),
    created_date       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   varchar(50),
    last_modified_date timestamp
);

-- Add comment to the table and columns
COMMENT
ON TABLE dictionaries IS '字典表';
COMMENT
ON COLUMN dictionaries.id IS '主键';
COMMENT
ON COLUMN dictionaries.name IS '名称';
COMMENT
ON COLUMN dictionaries.superior_id IS '上级ID';
COMMENT
ON COLUMN dictionaries.description IS '描述';
COMMENT
ON COLUMN dictionaries.enabled IS '是否启用';
COMMENT
ON COLUMN dictionaries.created_by IS '创建者';
COMMENT
ON COLUMN dictionaries.created_date IS '创建时间';
COMMENT
ON COLUMN dictionaries.last_modified_by IS '最后修改者';
COMMENT
ON COLUMN dictionaries.last_modified_date IS '最后修改时间';

-- Drop table if exists messages
DROP TABLE IF EXISTS messages;

-- Create table messages
CREATE TABLE messages
(
    id                 bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title              varchar(255) NOT NULL,
    content            varchar(1000),
    is_read            boolean      NOT NULL DEFAULT false,
    receiver           varchar(50)  NOT NULL,
    description        varchar(255),
    enabled            boolean      NOT NULL DEFAULT true,
    created_by         varchar(50),
    created_date       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   varchar(50)  NOT NULL,
    last_modified_date timestamp
);

-- Add comment to the table and columns
COMMENT
ON TABLE messages IS '消息表';
COMMENT
ON COLUMN messages.id IS '主键';
COMMENT
ON COLUMN messages.title IS '标题';
COMMENT
ON COLUMN messages.content IS '内容';
COMMENT
ON COLUMN messages.is_read IS '是否已读';
COMMENT
ON COLUMN messages.receiver IS '接收者';
COMMENT
ON COLUMN messages.description IS '描述';
COMMENT
ON COLUMN messages.enabled IS '是否启用';
COMMENT
ON COLUMN messages.created_by IS '创建者';
COMMENT
ON COLUMN messages.created_date IS '创建时间';
COMMENT
ON COLUMN messages.last_modified_by IS '最后修改者';
COMMENT
ON COLUMN messages.last_modified_date IS '最后修改时间';


-- Drop table if exists access_logs
DROP TABLE IF EXISTS access_logs;

-- Create table access_logs
CREATE TABLE access_logs
(
    id                 bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    api                varchar(10),
    method             varchar(10),
    params             varchar(255),
    body               json,
    ip                 inet,
    location           varchar(50),
    status_code        integer,
    response_times     bigint,
    response_message   varchar(255),
    enabled            boolean   NOT NULL DEFAULT true,
    created_by         varchar(50),
    created_date       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   varchar(50),
    last_modified_date timestamp
);

-- Add comment to the table and columns
COMMENT
ON TABLE access_logs IS '访问日志表';
COMMENT
ON COLUMN access_logs.id IS '主键';
COMMENT
ON COLUMN access_logs.api IS '内容';
COMMENT
ON COLUMN access_logs.http_method IS '内容';
COMMENT
ON COLUMN access_logs.params IS '内容';
COMMENT
ON COLUMN access_logs.body IS '内容';
COMMENT
ON COLUMN access_logs.ip IS 'IP地址';
COMMENT
ON COLUMN access_logs.location IS '位置';
COMMENT
ON COLUMN access_logs.status_code IS 'HTTP状态码';
COMMENT
ON COLUMN access_logs.response_times IS '响应时间';
COMMENT
ON COLUMN access_logs.response_message IS '响应消息';
COMMENT
ON COLUMN access_logs.enabled IS '是否启用';
COMMENT
ON COLUMN access_logs.created_by IS '创建者';
COMMENT
ON COLUMN access_logs.created_date IS '创建时间';
COMMENT
ON COLUMN access_logs.last_modified_by IS '最后修改者';
COMMENT
ON COLUMN access_logs.last_modified_date IS '最后修改时间';


-- Drop table if exists operation_logs
DROP TABLE IF EXISTS operation_logs;

-- Create table operation_logs
CREATE TABLE operation_logs
(
    id                 bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ip                 inet,
    location           varchar(50),
    content            varchar(1000),
    user_agent         varchar(255),
    http_method        varchar(10),
    url                varchar(255),
    status_code        integer,
    operated_times     bigint,
    response_message   varchar(255),
    referer            varchar(255),
    session_id         varchar(50),
    device_type        varchar(20),
    os                 varchar(50),
    browser            varchar(50),
    enabled            boolean   NOT NULL DEFAULT true,
    created_by         varchar(50),
    created_date       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by   varchar(50),
    last_modified_date timestamp
);

-- Add comment to the table and columns
COMMENT
ON TABLE operation_logs IS '访问日志表';
COMMENT
ON COLUMN operation_logs.id IS '主键';
COMMENT
ON COLUMN operation_logs.ip IS 'IP地址';
COMMENT
ON COLUMN operation_logs.location IS '位置';
COMMENT
ON COLUMN operation_logs.content IS '内容';
COMMENT
ON COLUMN operation_logs.user_agent IS '用户代理信息';
COMMENT
ON COLUMN operation_logs.http_method IS 'HTTP方法';
COMMENT
ON COLUMN operation_logs.url IS '请求URL';
COMMENT
ON COLUMN operation_logs.status_code IS 'HTTP状态码';
COMMENT
ON COLUMN operation_logs.operated_time IS '操作时间';
COMMENT
ON COLUMN operation_logs.referer IS '来源页面';
COMMENT
ON COLUMN operation_logs.session_id IS '会话标识符';
COMMENT
ON COLUMN operation_logs.device_type IS '设备类型';
COMMENT
ON COLUMN operation_logs.os IS '操作系统';
COMMENT
ON COLUMN operation_logs.browser IS '浏览器';
COMMENT
ON COLUMN operation_logs.enabled IS '是否启用';
COMMENT
ON COLUMN operation_logs.created_by IS '创建者';
COMMENT
ON COLUMN operation_logs.created_date IS '创建时间';
COMMENT
ON COLUMN operation_logs.last_modified_by IS '最后修改者';
COMMENT
ON COLUMN operation_logs.last_modified_date IS '最后修改时间';