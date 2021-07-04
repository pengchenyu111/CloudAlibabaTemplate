package com.pcy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author PengChenyu
 * @since 2021-06-30 21:41:58
 */
@ApiModel(value = "com-pcy-domain-MovieDetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "movie_detail")
public class MovieDetail implements Serializable {

    private static final long serialVersionUID = 8616734413744982833L;

    /**
     * 豆瓣id
     */
    @TableId(value = "douban_id", type = IdType.INPUT)
    @ApiModelProperty(value = "豆瓣id")
    private Integer doubanId;

    /**
     * 电影名
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "电影名")
    private String title;

    /**
     * 电影简介
     */
    @TableField(value = "brief_instruction")
    @ApiModelProperty(value = "电影简介")
    private String briefInstruction;

    /**
     * 导演列表,/分割，注意两边有空格
     */
    @TableField(value = "directors")
    @ApiModelProperty(value = "导演列表,/分割，注意两边有空格")
    private String directors;

    /**
     * 编剧列表,/分割，注意两边有空格
     */
    @TableField(value = "screenwriters")
    @ApiModelProperty(value = "编剧列表,/分割，注意两边有空格")
    private String screenwriters;

    /**
     * 演员列表,/分割，注意两边有空格
     */
    @TableField(value = "casts")
    @ApiModelProperty(value = "演员列表,/分割，注意两边有空格")
    private String casts;

    /**
     * 类型列表,/分割，注意两边有空格
     */
    @TableField(value = "types")
    @ApiModelProperty(value = "类型列表,/分割，注意两边有空格")
    private String types;

    /**
     * 制片国家/地区
     */
    @TableField(value = "production_country_area")
    @ApiModelProperty(value = "制片国家/地区")
    private String productionCountryArea;

    /**
     * 语言
     */
    @TableField(value = "language")
    @ApiModelProperty(value = "语言")
    private String language;

    /**
     * 上映日期列表,/分割，注意两边有空格
     */
    @TableField(value = "publish_date")
    @ApiModelProperty(value = "上映日期列表,/分割，注意两边有空格")
    private String publishDate;

    /**
     * 片长
     */
    @TableField(value = "runtime")
    @ApiModelProperty(value = "片长")
    private String runtime;

    /**
     * 评分分数，10分制
     */
    @TableField(value = "rating_score")
    @ApiModelProperty(value = "评分分数，10分制")
    private Double ratingScore;

    /**
     * 评分星级，5星制
     */
    @TableField(value = "rating_star")
    @ApiModelProperty(value = "评分星级，5星制")
    private Integer ratingStar;

    /**
     * 评分人数
     */
    @TableField(value = "rating_num")
    @ApiModelProperty(value = "评分人数")
    private Integer ratingNum;

    /**
     * 评5星占比
     */
    @TableField(value = "rating_5_star_weight")
    @ApiModelProperty(value = "评5星占比")
    private String rating5StarWeight;

    /**
     * 评4星占比
     */
    @TableField(value = "rating_4_star_weight")
    @ApiModelProperty(value = "评4星占比")
    private String rating4StarWeight;

    /**
     * 评3星占比
     */
    @TableField(value = "rating_3_star_weight")
    @ApiModelProperty(value = "评3星占比")
    private String rating3StarWeight;

    /**
     * 评2星占比
     */
    @TableField(value = "rating_2_star_weight")
    @ApiModelProperty(value = "评2星占比")
    private String rating2StarWeight;

    /**
     * 评1星占比
     */
    @TableField(value = "rating_1_star_weight")
    @ApiModelProperty(value = "评1星占比")
    private String rating1StarWeight;

    /**
     * 好于其他类型影片占比，列表
     */
    @TableField(value = "better_than")
    @ApiModelProperty(value = "好于其他类型影片占比，列表")
    private String betterThan;

    /**
     * 豆瓣电影链接
     */
    @TableField(value = "douban_url")
    @ApiModelProperty(value = "豆瓣电影链接")
    private String doubanUrl;

    /**
     * 电影海报链接
     */
    @TableField(value = "cover_url")
    @ApiModelProperty(value = "电影海报链接")
    private String coverUrl;

    /**
     * IMDb链接
     */
    @TableField(value = "imdb_url")
    @ApiModelProperty(value = "IMDb链接")
    private String imdbUrl;

    /**
     * 电影图片列表，逗号分割
     */
    @TableField(value = "img_list")
    @ApiModelProperty(value = "电影图片列表，逗号分割")
    private String imgList;

}