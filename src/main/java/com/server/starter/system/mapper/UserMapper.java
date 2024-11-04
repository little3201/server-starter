package com.server.starter.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * user mapper.
 *
 * @author wq li
 */
@Mapper
public interface UserMapper {

    @Update("UPDATE users SET enabled = NOT enabled WHERE id = #{id}")
    boolean updateEnabledById(Long id);
}
