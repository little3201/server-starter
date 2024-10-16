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

package com.server.starter.service.impl;

import com.server.starter.domain.Message;
import com.server.starter.dto.MessageDTO;
import com.server.starter.repository.MessageRepository;
import com.server.starter.service.MessageService;
import com.server.starter.vo.MessageVO;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * message service impl.
 *
 * @author wq li
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    /**
     * <p>Constructor for MessageServiceImpl.</p>
     *
     * @param messageRepository a {@link com.server.starter.repository.MessageRepository} object
     */
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<MessageVO> retrieve(int page, int size, String sortBy, boolean descending, String title) {
        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC,
                StringUtils.hasText(sortBy) ? sortBy : "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return messageRepository.findAll(pageable).map(this::convert);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageVO fetch(Long id) {
        Assert.notNull(id, "id must not be null.");
        Message message = messageRepository.findById(id).orElse(null);
        return this.convert(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageVO create(MessageDTO dto) {
        Message message = new Message();
        BeanCopier copier = BeanCopier.create(MessageDTO.class, Message.class, false);
        copier.copy(dto, message, null);

        messageRepository.save(message);
        return this.convert(message);
    }

    /**
     * 转换为输出对象
     *
     * @return ExampleMatcher
     */
    private MessageVO convert(Message message) {
        MessageVO vo = new MessageVO();
        BeanCopier copier = BeanCopier.create(Message.class, MessageVO.class, false);
        copier.copy(message, vo, null);
        return vo;
    }
}