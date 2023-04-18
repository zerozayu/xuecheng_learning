package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhangyu
* @description 针对表【course_category(课程分类)】的数据库操作Service
* @createDate 2023-04-18 23:49:43
*/
public interface CourseCategoryService extends IService<CourseCategory> {
    /**
     * 课程分类树形结构查询
     *
     * @return
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
