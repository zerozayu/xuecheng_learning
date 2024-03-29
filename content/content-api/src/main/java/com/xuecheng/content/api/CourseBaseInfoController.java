package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 课程信息编辑接口
 *
 * @author zhangyu
 * @date 2023/4/14 16:32
 */
@RestController
@Api(value = "课程信息编辑接口", tags = "课程信息编辑接口")
public class CourseBaseInfoController {
    private final CourseBaseService courseBaseService;

    public CourseBaseInfoController(CourseBaseService courseBaseService) {
        this.courseBaseService = courseBaseService;
    }

    @ApiOperation(value = "查询所有课程")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParams) {

        return courseBaseService.queryCourseBaseList(pageParams, queryCourseParams);
    }

    @ApiOperation(value = "新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@Validated(ValidationGroups.Insert.class) @RequestBody AddCourseDto addCourseDto) {
        String companyId = "1232141425";
        return courseBaseService.createCourseBase(companyId, addCourseDto);
    }

    @ApiOperation(value = "查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable(value = "courseId") String courseId) {
        return courseBaseService.getCourseBaseInfo(courseId);
    }

    @ApiOperation(value = "修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated EditCourseDto dto) {
        //机构id，由于认证系统没有上线暂时硬编码
        String companyId = "1232141425";

        return courseBaseService.updateCourseBase(companyId, dto);
    }

    @DeleteMapping("/course/{courseId}")
    public void removeCourse(@PathVariable String courseId) {

        courseBaseService.removeCourse(courseId);
    }

}
