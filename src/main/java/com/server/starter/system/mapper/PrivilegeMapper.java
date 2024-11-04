package com.server.starter.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * privilege mapper.
 *
 * @author wq li
 */
@Mapper
public interface PrivilegeMapper {

    @Update("UPDATE privileges SET enabled = NOT enabled WHERE id = #{id}")
    boolean updateEnabledById(Long id);
}
