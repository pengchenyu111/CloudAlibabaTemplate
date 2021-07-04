package com.pcy.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcy.domain.MovieUser;
import com.pcy.mapper.MovieUserMapper;
import com.pcy.service.MovieUserService;
/**
 * 
 * @author PengChenyu
 * @since 2021-07-01 20:43:24
 */
@Service
public class MovieUserServiceImpl extends ServiceImpl<MovieUserMapper, MovieUser> implements MovieUserService{

}
