package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    /**
     * 课程计划关联的媒资信息
     */
    private TeachplanMedia teachplanMedia;

    /**
     * 子节点
     */
    private List<TeachplanDto> teachPlanTreeNodes;
}
