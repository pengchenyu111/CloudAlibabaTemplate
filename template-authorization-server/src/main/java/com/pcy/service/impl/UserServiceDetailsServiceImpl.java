package com.pcy.service.impl;

import com.pcy.constant.LoginConstant;
import com.pcy.domain.MovieUser;
import com.pcy.domain.SysUser;
import com.pcy.mapper.GeneralUserMapper;
import com.pcy.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Service("userServiceDetailsServiceImpl")
public class UserServiceDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private GeneralUserMapper generalUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 通过login_type区分是管理员还是普通用户登录
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("登录类型不能为null");
        }
        UserDetails userDetails = null;
        try {
            // 若通过refresh_token获取新token，则对username进行纠正
            String grantType = requestAttributes.getRequest().getParameter("grant_type");
            if (LoginConstant.REFRESH_TYPE.equals(grantType.toUpperCase())) {
                username = adjustUsername(username, loginType);
            }
            switch (loginType) {
                case LoginConstant.ADMIN_TYPE:
                    userDetails = loadSysUserByUsername(username);
                    break;
                case LoginConstant.GENERAL_USER_TYPE:
                    userDetails = loadGeneralUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登录方式:" + loginType);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException("用户名" + username + "不存在");
        }
        return userDetails;
    }

    /**
     * 纠正用户的名称
     * 这里的username指的是用户登录时的账户
     * id --> username
     *
     * @param username  用户名
     * @param loginType 用户类型
     * @return
     */
    private String adjustUsername(String username, String loginType) {
        // 管理员
        if (LoginConstant.ADMIN_TYPE.equals(loginType)) {
            return sysUserMapper.selectUsernameById(username);
        }
        // 普通用户
        if (LoginConstant.GENERAL_USER_TYPE.equals(loginType)) {
            return generalUserMapper.selectUsernameById(username);
        }
        return username;
    }

    /**
     * 管理员登录
     *
     * @param username
     * @return
     */
    private UserDetails loadSysUserByUsername(String username) {
        SysUser sysUser = sysUserMapper.selectSysUserByUsername(username);
        return new User(
                String.valueOf(sysUser.getId()),
                sysUser.getPassword(),
                // 是否可用
                sysUser.getStatus() == 1,
                // 账号未过期
                true,
                // 凭证未过期
                true,
                // 账号未锁定
                true,
                // 在这里从数据库获取管理员的权限，之后用 @PreAuthorize("hasAuthority('xxx')")做访问权限控制
                getSysUserPermissions(sysUser.getId())
        );
    }

    /**
     * 获取管理员的权限
     *
     * @param id
     * @return
     */
    private Collection<? extends GrantedAuthority> getSysUserPermissions(long id) {
        // 从数据库获取权限数据
        List<String> permissions = new ArrayList<>();
        permissions.add("permission1");
        permissions.add("permission2");
        permissions.add("permission3");
        return permissions.stream()
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    /**
     * 会员的登录
     *
     * @param username
     * @return
     */
    private UserDetails loadGeneralUserByUsername(String username) {
        MovieUser generalUser = generalUserMapper.selectGeneralUserByUsername(username);
        return new User(
                String.valueOf(generalUser.getUserId()),
                generalUser.getPassword(),
                // 是否可用
                true,
                // 账号未过期
                true,
                // 凭证未过期
                true,
                // 账号未锁定
                true,
                // 在这里从数据库获取普通用户的权限，之后用 @PreAuthorize("hasAuthority('xxx')")做访问权限控制
                getGeneralUserPermissions(generalUser.getUserId())
        );
    }

    /**
     * 获取管理员的权限
     *
     * @param id
     * @return
     */
    private Collection<? extends GrantedAuthority> getGeneralUserPermissions(long id) {
        // 从数据库获取权限数据
        List<String> permissions = new ArrayList<>();
        permissions.add("permission1");
        permissions.add("permission2");
        permissions.add("permission3");
        return permissions.stream()
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }


}


