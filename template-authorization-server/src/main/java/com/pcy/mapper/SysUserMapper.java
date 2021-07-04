package com.pcy.mapper;

import com.pcy.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author PengChenyu
 * @since 2021-06-30 15:55:44
 */
@Mapper
public interface SysUserMapper {

    /**
     * 通过 id 去找 username
     *
     * @param id
     * @return
     */
    String selectUsernameById(String id);

    /**
     * 通过 username 去找管理员的全部信息
     *
     * @param username
     * @return
     */
    SysUser selectSysUserByUsername(String username);
}