package com.xuecheng.content.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 课程计划
 *
 * @TableName teachplan
 */
@Data
public class Teachplan implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    protected String id;
    /**
     * 课程计划名称
     */
    @NotBlank(message = "[课程计划名称]不能为空")
    @Size(max = 64, message = "编码长度不能超过64")
    @ApiModelProperty("课程计划名称")
    @Length(max = 64, message = "编码长度不能超过64")
    private String pname;
    /**
     * 课程计划父级Id
     */
    @NotNull(message = "[课程计划父级Id]不能为空")
    @ApiModelProperty("课程计划父级Id")
    private String parentid;
    /**
     * 层级，分为1、2、3级
     */
    @NotNull(message = "[层级，分为1、2、3级]不能为空")
    @ApiModelProperty("层级，分为1、2、3级")
    private Integer grade;
    /**
     * 课程类型:1视频、2文档
     */
    @Size(max = 10, message = "编码长度不能超过10")
    @ApiModelProperty("课程类型:1视频、2文档")
    @Length(max = 10, message = "编码长度不能超过10")
    private String mediaType;
    /**
     * 开始直播时间
     */
    @ApiModelProperty("开始直播时间")
    private LocalDate startTime;
    /**
     * 直播结束时间
     */
    @ApiModelProperty("直播结束时间")
    private LocalDate endTime;
    /**
     * 章节及课程时介绍
     */
    @Size(max = 500, message = "编码长度不能超过500")
    @ApiModelProperty("章节及课程时介绍")
    @Length(max = 500, message = "编码长度不能超过500")
    private String description;
    /**
     * 时长，单位时:分:秒
     */
    @Size(max = 30, message = "编码长度不能超过30")
    @ApiModelProperty("时长，单位时:分:秒")
    @Length(max = 30, message = "编码长度不能超过30")
    private String timelength;
    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private Integer orderby;
    /**
     * 课程标识
     */
    @NotNull(message = "[课程标识]不能为空")
    @ApiModelProperty("课程标识")
    private String courseId;
    /**
     * 课程发布标识
     */
    @ApiModelProperty("课程发布标识")
    private String coursePubId;
    /**
     * 状态（1正常  0删除）
     */
    @NotNull(message = "[状态（1正常  0删除）]不能为空")
    @ApiModelProperty("状态（1正常  0删除）")
    private Integer status;
    /**
     * 是否支持试学或预览（试看）
     */
    @ApiModelProperty("是否支持试学或预览（试看）")
    private String isPreview;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDate createDate;
    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDate changeDate;
}
