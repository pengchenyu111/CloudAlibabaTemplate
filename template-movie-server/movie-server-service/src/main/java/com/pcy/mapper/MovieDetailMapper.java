package com.pcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcy.domain.MovieDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author PengChenyu
 * @since 2021-06-30 21:41:58
 */
@Mapper
public interface MovieDetailMapper extends BaseMapper<MovieDetail> {

    /**
     * 根据电影id修改电影标题
     *
     * @param id       电影id
     * @param newTitle 电影新标题
     * @return
     */
    int updateTitleById(@Param("id") Integer id, @Param("newTitle") String newTitle);
}