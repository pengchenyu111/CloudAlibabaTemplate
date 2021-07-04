package com.pcy.controller;

import com.pcy.constant.ErrorCodeMsg;
import com.pcy.domain.MovieDetail;
import com.pcy.model.ResponseObject;
import com.pcy.service.MovieDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PengChenyu
 * @since 2021-06-30 21:50:36
 */
@RestController
@RequestMapping("movie_detail")
@Api(value = "movie_detail", tags = "电影详情")
public class MovieDetailController {

    @Autowired
    private MovieDetailService movieDetailService;

    @ApiOperation(value = "根据电影id获取电影详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "电影id"),
    })
    @GetMapping("/{id}")
    public ResponseObject<MovieDetail> queryMovie(@PathVariable("id") Integer id) {
        MovieDetail movieDetail = movieDetailService.getById(id);
        if (movieDetail == null) {
            return ResponseObject.failed(ErrorCodeMsg.QUERY_NULL_CODE, ErrorCodeMsg.QUERY_NULL_MESSAGE, null);
        }
        return ResponseObject.success(ErrorCodeMsg.QUERY_SUCCESS_CODE, ErrorCodeMsg.QUERY_SUCCESS_MESSAGE, movieDetail);

    }
}
