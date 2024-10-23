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
package com.server.starter.system.service.impl;

import com.server.starter.system.domain.User;
import com.server.starter.system.dto.UserDTO;
import com.server.starter.system.repository.UserRepository;
import com.server.starter.system.service.UserService;
import com.server.starter.system.vo.UserVO;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * user service impl.
 *
 * @author wq li
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * <p>Constructor for UserServiceImpl.</p>
     *
     * @param userRepository a {@link UserRepository} object
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<UserVO> retrieve(int page, int size, String sortBy, boolean descending, String username) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return userRepository.findAll(pageable).map(this::convert);
    }

    @Override
    public UserVO findByUsername(String username) {
        Assert.hasText(username, "username must not be blank.");

        return userRepository.findByUsername(username).map(this::convert).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");

        return userRepository.findById(id).map(this::convert).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exist(String username) {
        Assert.hasText(username, "username must not be blank.");

        return userRepository.existsByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserVO create(UserDTO dto) {
        User user = new User();
        BeanCopier copier = BeanCopier.create(UserDTO.class, User.class, false);
        copier.copy(dto, user, null);
        user.setPassword("{noop}123456");
        user = userRepository.save(user);
        return this.convert(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserVO modify(Long id, UserDTO dto) {
        Assert.notNull(id, "id must not be null.");
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(UserDTO.class, User.class, false);
        copier.copy(dto, user, null);

        user = userRepository.save(user);
        return this.convert(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id) {
        Assert.notNull(id, "id must not be null.");
        userRepository.deleteById(id);
    }

    /**
     * 转换为输出对象
     *
     * @return ExampleMatcher
     */
    private UserVO convert(User user) {
        UserVO vo = new UserVO();
        BeanCopier copier = BeanCopier.create(User.class, UserVO.class, false);
        copier.copy(user, vo, null);
        return vo;
    }

}
