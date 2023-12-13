package com.xuecheng.content.model.dto;

import lombok.Data;

/**
 * 保存课程计划dto,包括新增和\修改
 *
 * @author zhangyu
 * @date 2023/12/8 15:56
 */
@Data
public class SaveTeachplanDto {
    /***
     * 教学计划id
     */
    private String id;

    /**
     * 课程计划名称
     */
    private String pname;

    /**
     * 课程计划父级Id
     */
    private String parentid;

    /**
     * 层级，分为1、2、3级
     */
    private Integer grade;

    /**
     * 课程类型:1视频、2文档
     */
    private String mediaType;

    /**
     * 课程标识
     */
    private String courseId;

    /**
     * 课程发布标识
     */
    private String coursePubId;

    /**
     * 是否支持试学或预览（试看）
     */
    private String isPreview;
}
