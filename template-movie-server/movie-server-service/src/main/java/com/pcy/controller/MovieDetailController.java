package com.pcy.controller;

import com.pcy.constant.ErrorCodeMsg;
import com.pcy.domain.MailTemplate;
import com.pcy.domain.MovieDetail;
import com.pcy.feign.MailTemplateFeign;
import com.pcy.model.ResponseObject;
import com.pcy.service.MovieDetailService;
import com.pcy.vo.MailTemplateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private MailTemplateFeign mailTemplateFeign;

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

    @ApiOperation(value = "根据电影id修改电影标题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "电影id"),
            @ApiImplicitParam(name = "newTitle", value = "电影新标题"),
    })
    @PutMapping("/title/{id}")
    public ResponseObject<MovieDetail> updateTitleById(@PathVariable("id") Integer id, @RequestBody Map<String, String> map) {
        MovieDetail movieDetail = movieDetailService.updateTitleById(id, map.get("newTitle"));
        if (movieDetail == null) {
            return ResponseObject.failed(ErrorCodeMsg.UPDATE_FAILED_CODE, ErrorCodeMsg.UPDATE_FAILED_MESSAGE, null);
        }
        return ResponseObject.success(ErrorCodeMsg.UPDATE_SUCCESS_CODE, ErrorCodeMsg.UPDATE_SUCCESS_MESSAGE, movieDetail);
    }

    /**
     * feign测试
     * 注意@EnableFeignClients要在消费端和服务端都写上，不然无法注入mailTemplateFeign
     * @return
     */
    @GetMapping("/test")
    public ResponseObject<List<MailTemplate>> test() {
        MailTemplateVo mailTemplateVo = MailTemplateVo.builder()
                .id(1)
                .build();
        return mailTemplateFeign.queryMailTemplate(mailTemplateVo);
    }
}
