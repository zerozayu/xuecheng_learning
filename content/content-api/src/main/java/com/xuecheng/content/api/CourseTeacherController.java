package com.xuecheng.content.api;

import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 师资信息controller
 *
 * @author zhangyu
 * @date 2024/1/9 22:59
 */
@RestController
@RequestMapping("/courseTeacher")
public class CourseTeacherController {
    private final CourseTeacherService courseTeacherService;

    public CourseTeacherController(CourseTeacherService courseTeacherService) {
        this.courseTeacherService = courseTeacherService;
    }

    /**
     * 查询课程下的师资信息列表
     *
     * @param courseId
     * @return
     */
    @GetMapping("/list/{courseId}")
    public List<CourseTeacher> listCourseTeachers(@PathVariable String courseId) {
        return courseTeacherService.listCourseTeachers(courseId);
    }

    /**
     * 新增教师
     *
     * @param courseTeacher
     */
    @PostMapping
    public void saveCourseTeacher(@RequestBody CourseTeacher courseTeacher) {
        courseTeacherService.saveCourseTeacher(courseTeacher);
    }


    @DeleteMapping("/course/{courseId}/{courseTeacherId}")
    public void removeCourseTeacher(@PathVariable String courseId, @PathVariable String courseTeacherId){
        courseTeacherService.removeCourseTeacher(courseId, courseTeacherId);
    }
}
