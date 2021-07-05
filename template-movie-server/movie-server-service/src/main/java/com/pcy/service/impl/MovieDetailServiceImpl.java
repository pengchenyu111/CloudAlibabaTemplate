package com.pcy.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcy.mapper.MovieDetailMapper;
import com.pcy.domain.MovieDetail;
import com.pcy.service.MovieDetailService;

/**
 * @author PengChenyu
 * @since 2021-06-30 21:41:58
 */
@Service
public class MovieDetailServiceImpl extends ServiceImpl<MovieDetailMapper, MovieDetail> implements MovieDetailService {

    @Autowired
    MovieDetailMapper movieDetailMapper;

    /**
     * 根据电影id修改电影标题
     *
     * @param id       电影id
     * @param newTitle 电影新标题
     * @return
     */
    @GlobalTransactional
    @Override
    public MovieDetail updateTitleById(Integer id, String newTitle) {
        movieDetailMapper.updateTitleById(id, newTitle);
        return movieDetailMapper.selectById(id);
    }
}
