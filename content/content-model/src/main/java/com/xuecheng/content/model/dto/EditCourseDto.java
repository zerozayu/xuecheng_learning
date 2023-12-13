package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 修改课程dto
 *
 * @author zhangyu
 * @date 2023/12/5 22:47
 */
@ApiModel(value = "EditCourseDto", description = "修改课程基本信息")
@EqualsAndHashCode(callSuper = true)
@Data
public class EditCourseDto extends AddCourseDto {

    @ApiModelProperty(value = "课程id", required = true)
    private String id;

}
