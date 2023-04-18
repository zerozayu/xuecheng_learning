package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 课程分类树形节点dto
 *
 * @author zhangyu
 * @date 2023/4/18 23:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<CourseCategoryTreeDto> childrenTreeNodes;
}
