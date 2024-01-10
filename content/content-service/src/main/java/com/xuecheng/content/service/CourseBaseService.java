package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zhangyu
 * @description 针对表【course_base(课程基本信息)】的数据库操作Service
 * @createDate 2023-04-17 11:45:05
 */
public interface CourseBaseService extends IService<CourseBase> {
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 添加课程基本信息
     *
     * @param companyId    教学机构id
     * @param addCourseDto 课程基本信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     */
    CourseBaseInfoDto createCourseBase(String companyId, AddCourseDto addCourseDto);

    /**
     * 查询课程基本信息
     * @param courseId
     * @return
     */
    CourseBaseInfoDto getCourseBaseInfo(String courseId);

    /**
     * 修改课程基本信息
     * @param companyId
     * @param dto
     * @return
     */
    CourseBaseInfoDto updateCourseBase(String companyId, EditCourseDto dto);

    void removeCourse(String courseId);
}
