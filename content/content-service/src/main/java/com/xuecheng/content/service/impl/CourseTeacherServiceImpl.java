package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhangyu
 * @description 针对表【course_teacher(课程-教师关系表)】的数据库操作Service实现
 * @createDate 2024-01-09 23:03:04
 */
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher>
        implements CourseTeacherService {

    private final CourseBaseMapper courseBaseMapper;
    private final CourseTeacherMapper courseTeacherMapper;

    public CourseTeacherServiceImpl(CourseBaseMapper courseBaseMapper, CourseTeacherMapper courseTeacherMapper) {
        this.courseBaseMapper = courseBaseMapper;
        this.courseTeacherMapper = courseTeacherMapper;
    }


    @Override
    public List<CourseTeacher> listCourseTeachers(String courseId) {
        LambdaQueryChainWrapper<CourseTeacher> lambdaQueryChainWrapper =
                new LambdaQueryChainWrapper<>(courseTeacherMapper)
                        .eq(CourseTeacher::getCourseId, courseId);
        return lambdaQueryChainWrapper.list();
    }

    @Override
    public void saveCourseTeacher(CourseTeacher courseTeacher) {
        courseTeacher.setCreateDate(LocalDateTime.now());
        int insert = courseTeacherMapper.insert(courseTeacher);
        if (insert <= 0) {
            XueChengPlusException.cast("新增课程师资信息失败");
        }
    }

    @Override
    public void removeCourseTeacher(String courseId, String courseTeacherId) {
        LambdaQueryWrapper<CourseTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CourseTeacher::getCourseId, Long.parseLong(courseId));
        lambdaQueryWrapper.eq(CourseTeacher::getId, Long.parseLong(courseTeacherId));

        int delete = courseTeacherMapper.delete(lambdaQueryWrapper);
        if (delete <= 0) {
            XueChengPlusException.cast("删除课程师资信息失败");
        }
    }
}




