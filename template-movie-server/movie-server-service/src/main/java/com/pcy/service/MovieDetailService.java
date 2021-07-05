package com.pcy.service;

import com.pcy.domain.MovieDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author PengChenyu
 * @since 2021-06-30 21:41:58
 */
public interface MovieDetailService extends IService<MovieDetail> {


    /**
     * 根据电影id修改电影标题
     *
     * @param id       电影id
     * @param newTitle 电影新标题
     * @return
     */
    MovieDetail updateTitleById(Integer id, String newTitle);
}
