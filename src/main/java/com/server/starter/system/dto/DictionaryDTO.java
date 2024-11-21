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

package com.server.starter.system.dto;

import com.server.starter.system.bo.DictionaryBO;

/**
 * dto class for dictionary.
 *
 * @author wq li
 */
public class DictionaryDTO extends DictionaryBO {

    /**
     * superior
     */
    private Long superiorId;

    /**
     * <p>Getter for the field <code>superiorId</code>.</p>
     *
     * @return a {@link java.lang.Long} object
     */
    public Long getSuperiorId() {
        return superiorId;
    }

    /**
     * {@inheritDoc}
     */
    public void setSuperiorId(Long superiorId) {
        this.superiorId = superiorId;
    }
}
