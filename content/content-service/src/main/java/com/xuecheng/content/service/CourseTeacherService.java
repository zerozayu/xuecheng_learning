package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

/**
* @author zhangyu
* @description 针对表【course_teacher(课程-教师关系表)】的数据库操作Service
* @createDate 2024-01-09 23:03:04
*/
public interface CourseTeacherService extends IService<CourseTeacher> {

    List<CourseTeacher> listCourseTeachers(String courseId);

    void saveCourseTeacher(CourseTeacher courseTeacher);

    void removeCourseTeacher(String courseId, String courseTeacherId);
}
