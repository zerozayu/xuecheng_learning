package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 课程查询参数Dto
 *
 * @author zhangyu
 * @date 2023/4/14 16:15
 */
@Data
@ToString
public class QueryCourseParamsDto {
    //审核状态
    @ApiModelProperty(name = "auditStatus", value = "审核状态", example = "true")
    private String auditStatus;

    //课程名称
    @ApiModelProperty(name = "name", value = "课程名称")
    private String courseName;

    //发布状态
    @ApiModelProperty(name = "publishStatus", value = "发布状态")
    private String publishStatus;
}
