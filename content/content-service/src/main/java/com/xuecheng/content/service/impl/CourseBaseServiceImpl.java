package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.*;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.*;
import com.xuecheng.content.service.CourseBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangyu
 * @description 针对表【course_base(课程基本信息)】的数据库操作Service实现
 * @createDate 2023-04-17 11:45:05
 */
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {

    private final CourseBaseMapper courseBaseMapper;
    private final CourseMarketMapper courseMarketMapper;
    private final CourseCategoryMapper courseCategoryMapper;
    private final TeachplanMapper teachplanMapper;
    private final CourseTeacherMapper courseTeacherMapper;

    public CourseBaseServiceImpl(CourseBaseMapper courseBaseMapper, CourseMarketMapper courseMarketMapper, CourseCategoryMapper courseCategoryMapper, TeachplanMapper teachplanMapper, CourseTeacherMapper courseTeacherMapper) {
        this.courseBaseMapper = courseBaseMapper;
        this.courseMarketMapper = courseMarketMapper;
        this.courseCategoryMapper = courseCategoryMapper;
        this.teachplanMapper = teachplanMapper;
        this.courseTeacherMapper = courseTeacherMapper;
    }

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        // 构建查询条件对象
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();

        // 构建查询条件,根据课程名称查询
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());

        //构建查询条件，根据课程审核状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());

        //构建查询条件，根据课程发布状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());

        queryWrapper.orderBy(true, false, CourseBase::getCreateDate);

        // 分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<CourseBase> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;

    }

    @Override
    public CourseBaseInfoDto createCourseBase(String companyId, AddCourseDto dto) {
        // //合法性校验
        // if (StringUtils.isBlank(dto.getName())) {
        //     throw new XueChengPlusException("课程名称为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getMt())) {
        //     throw new XueChengPlusException("课程分类为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getSt())) {
        //     throw new XueChengPlusException("课程分类为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getGrade())) {
        //     throw new XueChengPlusException("课程等级为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getTeachmode())) {
        //     throw new XueChengPlusException("教育模式为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getUsers())) {
        //     throw new XueChengPlusException("适应人群为空");
        // }
        //
        // if (StringUtils.isBlank(dto.getCharge())) {
        //     throw new XueChengPlusException("收费规则为空");
        // }

        //新增对象
        CourseBase courseBaseNew = new CourseBase();
        //将填写的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto, courseBaseNew);
        //设置审核状态
        courseBaseNew.setAuditStatus("202002");
        //设置发布状态
        courseBaseNew.setStatus("203001");
        //机构id
        courseBaseNew.setCompanyId(companyId);
        //添加时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //插入课程基本信息表
        int insert = courseBaseMapper.insert(courseBaseNew);

        if (insert <= 0) {
            XueChengPlusException.cast("新增课程基本信息失败");
        }

        //向课程营销表保存课程营销信息
        //课程营销信息
        CourseMarket courseMarketNew = new CourseMarket();
        String courseId = courseBaseNew.getId();
        BeanUtils.copyProperties(dto, courseMarketNew);
        courseMarketNew.setId(courseId);
        int i = saveCourseMarket(courseMarketNew);
        if (i <= 0) {
            XueChengPlusException.cast("保存课程营销信息失败");
        }

        //查询课程基本信息及营销信息并返回
        return getCourseBaseInfo(courseId);
    }


    //根据课程id查询课程基本信息，包括基本信息和营销信息
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(String courseId) {

        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }

        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;

    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(String companyId, EditCourseDto dto) {

        // 课程id
        String courseId = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }

        // 校验本机构只能修改本机构的课程
        if (!Objects.equals(companyId, courseBase.getCompanyId())) {
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }

        // 封装基本信息
        BeanUtils.copyProperties(dto, courseBase);
        courseBase.setChangeDate(LocalDateTime.now());
        int update = courseBaseMapper.updateById(courseBase);
        if (update <= 0) {
            XueChengPlusException.cast("修改课程基本信息失败");
        }

        // 封装营销信息的数据
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto, courseMarket);
        this.saveCourseMarket(courseMarket);

        // 查询课程信息
        return this.getCourseBaseInfo(courseId);
    }

    @Override
    @Transactional
    public void removeCourse(String courseId) {

        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }
        if (!Objects.equals(courseBase.getAuditStatus(), "202002")) {
            XueChengPlusException.cast("课程的审核状态为未提交方可删除");
        }

        // 删除课程相关的基本信息
        courseBaseMapper.deleteById(courseId);
        // 删除营销信息
        courseMarketMapper.deleteById(courseId);
        // 课程计划
        LambdaQueryWrapper<Teachplan> teachplanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teachplanLambdaQueryWrapper.eq(Teachplan::getCourseId, courseId);
        teachplanMapper.delete(teachplanLambdaQueryWrapper);
        // 删除课程教师信息
        LambdaQueryWrapper<CourseTeacher> courseTeacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        courseTeacherLambdaQueryWrapper.eq(CourseTeacher::getCourseId, courseId);
        courseTeacherMapper.delete(courseTeacherLambdaQueryWrapper);
    }


    //保存课程营销信息
    private int saveCourseMarket(CourseMarket courseMarketNew) {
        //收费规则
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isBlank(charge)) {
            XueChengPlusException.cast("收费规则没有选择");
        }
        //收费规则为收费
        if (charge.equals("201001")) {
            if (courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue() <= 0) {
                XueChengPlusException.cast("课程为收费价格不能为空且必须大于0");
            }
        }
        //根据id从课程营销表查询
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarketNew.getId());
        if (courseMarketObj == null) {
            return courseMarketMapper.insert(courseMarketNew);
        } else {
            BeanUtils.copyProperties(courseMarketNew, courseMarketObj);
            courseMarketObj.setId(courseMarketNew.getId());
            return courseMarketMapper.updateById(courseMarketObj);
        }
    }

}
