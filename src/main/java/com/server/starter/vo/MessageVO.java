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

package com.server.starter.vo;

import com.server.starter.bo.MessageBO;

/**
 * vo class for message.
 *
 * @author wq li
 */
public class MessageVO extends MessageBO {

    private Long id;


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

}