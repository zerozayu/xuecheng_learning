package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 课程计划树形dto
 *
 * @author zhangyu
 * @date 2023/12/6 22:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeachplanDto extends Teachplan {
    private static final long serialVersionUID = 1L;

    /**
     * 课程计划关联的媒资信息
     */
    private TeachplanMedia teachplanMedia;

    /**
     * 子节点
     */
    private List<TeachplanDto> teachPlanTreeNodes;
}
