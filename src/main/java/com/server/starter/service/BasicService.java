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

package com.server.starter.service;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * Interface providing basic service operations.
 */
public interface BasicService {

    /**
     * Creates a {@link Pageable} object for pagination and sorting.
     *
     * @param page       The page number (zero-based).
     * @param size       The size of the page (number of items per page).
     * @param sortBy     The field to sort by, or null for unsorted pagination.
     * @param descending Whether the sorting should be in descending order.
     * @return A {@link Pageable} instance configured with the provided parameters.
     */
    default Pageable pageable(int page, int size, String sortBy, boolean descending) {
        Pageable pageable;
        if (StringUtils.hasText(sortBy)) {
            Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return pageable;
    }

    default <S, T> T convert(S source, Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanCopier copier = BeanCopier.create(source.getClass(), targetClass, false);
            copier.copy(source, target, null);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Conversion error", e);
        }
    }

    default <S, T> T convert(S source, T target) {
        try {
            BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            copier.copy(source, target, null);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Conversion error", e);
        }
    }
}
