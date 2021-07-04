package com.pcy.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcy.mapper.MovieDetailMapper;
import com.pcy.domain.MovieDetail;
import com.pcy.service.MovieDetailService;
/**
 * 
 * @author PengChenyu
 * @since 2021-06-30 21:41:58
 */
@Service
public class MovieDetailServiceImpl extends ServiceImpl<MovieDetailMapper, MovieDetail> implements MovieDetailService{

}
