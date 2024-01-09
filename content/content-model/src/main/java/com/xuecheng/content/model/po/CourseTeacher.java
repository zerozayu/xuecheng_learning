package com.xuecheng.content.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 课程-教师关系表
 *
 * @TableName course_teacher
 */
@Data
public class CourseTeacher implements Serializable {

    /**
     * 主键
     */
    @NotNull(message = "[主键]不能为空")
    @ApiModelProperty("主键")
    private String id;
    /**
     * 课程标识
     */
    @ApiModelProperty("课程标识")
    private String courseId;
    /**
     * 教师标识
     */
    @Size(max = 60, message = "编码长度不能超过60")
    @ApiModelProperty("教师标识")
    @Length(max = 60, message = "编码长度不能超过60")
    private String teacherName;
    /**
     * 教师职位
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("教师职位")
    @Length(max = 255, message = "编码长度不能超过255")
    private String position;
    /**
     * 教师简介
     */
    @Size(max = 1024, message = "编码长度不能超过1024")
    @ApiModelProperty("教师简介")
    @Length(max = 1024, message = "编码长度不能超过1,024")
    private String introduction;
    /**
     * 照片
     */
    @Size(max = 1024, message = "编码长度不能超过1024")
    @ApiModelProperty("照片")
    @Length(max = 1024, message = "编码长度不能超过1,024")
    private String photograph;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;

}
