package com.pcy.mapper;

import com.pcy.domain.MovieUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author PengChenyu
 * @since 2021-06-30 15:48:44
 */
@Mapper
public interface GeneralUserMapper {

    /**
     * 通过 id 去找username
     *
     * @param id
     * @return
     */
    String selectUsernameById(String id);

    /**
     * 通过 username 获取普通用户的信息
     *
     * @param username
     * @return
     */
    MovieUser selectGeneralUserByUsername(String username);
}