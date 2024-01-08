package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangyu
 * @description 针对表【teachplan(课程计划)】的数据库操作Service实现
 * @createDate 2023-12-06 23:31:40
 */
@Data
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan>
        implements TeachplanService {

    private final TeachplanMapper teachplanMapper;

    public TeachplanServiceImpl(TeachplanMapper teachplanMapper) {
        this.teachplanMapper = teachplanMapper;
    }

    @Override
    public List<TeachplanDto> findTeachplanTree(String courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    @Transactional
    public void saveTeachpan(SaveTeachplanDto teachplanDto) {
        // 课程id
        String id = teachplanDto.getId();
        if (id != null) {
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(teachplanDto, teachplan);
            teachplan.setChangeDate(LocalDate.now());
            teachplanMapper.updateById(teachplan);
        } else {
            // 取出同父同级别的课程计划数量
            int count = this.getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            teachplanNew.setOrderby(count + 1);
            teachplanNew.setCreateDate(LocalDate.now());
            BeanUtils.copyProperties(teachplanDto, teachplanNew);
            teachplanMapper.insert(teachplanNew);
        }
    }

    @Override
    @Transactional
    public void removeTeachPlan(String teachPlanId) {

        Teachplan teachplan = teachplanMapper.selectById(teachPlanId);

        if (Objects.equals(teachplan.getGrade(), 1)) {

            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getParentid, teachPlanId);
            Long count = teachplanMapper.selectCount(queryWrapper);
            // 1. 大章节下面有小章节,不允许删除
            if (count > 0) {
                XueChengPlusException.cast("120409", "课程计划信息还有子级信息，无法操作");
            }
            // 2. 大章节下面没有小章节,可以正常删除
            else {
                teachplanMapper.deleteById(teachPlanId);
            }
        } else {
            // 3. 删除小章节,同时将关联的信息进行删除
            teachplanMapper.deleteById(teachPlanId);
            // todo (zhangyu, 2023-12-29, 16:50:35) : 删除关联资源
        }
    }

    @Override
    @Transactional
    public void moveUpTeachPlan(String teachPlanId) {
        // 查询课程计划
        Teachplan teachplan = teachplanMapper.selectById(teachPlanId);
        Integer orderby = teachplan.getOrderby();
        // 查询小于当前课程计划orderby的最大一个课程计划
        LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(Teachplan::getCourseId, teachplan.getCourseId())
                .eq(Teachplan::getGrade, teachplan.getGrade())
                .eq(teachplan.getGrade() != 1, Teachplan::getParentid, teachplan.getParentid())// 非层级1 的需要添加 parentid 的限制
                .lt(Teachplan::getOrderby, orderby)
                .orderByDesc(Teachplan::getOrderby)
                .last("limit 1");
        Teachplan toChangeTeachplan = teachplanMapper.selectOne(lambdaQueryWrapper);
        if (toChangeTeachplan != null) {
            teachplan.setOrderby(toChangeTeachplan.getOrderby());
            toChangeTeachplan.setOrderby(orderby);
            teachplanMapper.updateById(toChangeTeachplan);
            teachplanMapper.updateById(teachplan);
        }
    }

    @Override
    public void moveDownTeachPlan(String teachPlanId) {
        // 查询课程计划
        Teachplan teachplan = teachplanMapper.selectById(teachPlanId);
        Integer orderby = teachplan.getOrderby();
        // 查询大于当前课程计划orderby的最小一个课程计划
        LambdaQueryWrapper<Teachplan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(Teachplan::getCourseId, teachplan.getCourseId())
                .eq(Teachplan::getGrade, teachplan.getGrade())
                .eq(teachplan.getGrade() != 1, Teachplan::getParentid, teachplan.getParentid())// 非层级1 的需要添加 parentid 的限制
                .gt(Teachplan::getOrderby, orderby)
                .orderByAsc(Teachplan::getOrderby)
                .last("limit 1");
        Teachplan toChangeTeachplan = teachplanMapper.selectOne(lambdaQueryWrapper);
        if (toChangeTeachplan != null) {
            teachplan.setOrderby(toChangeTeachplan.getOrderby());
            toChangeTeachplan.setOrderby(orderby);
            teachplanMapper.updateById(toChangeTeachplan);
            teachplanMapper.updateById(teachplan);
        }
    }

    /**
     * 获取最新的排序号
     *
     * @param courseId
     * @param parentid
     * @return
     */
    private int getTeachplanCount(String courseId, String parentid) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId, courseId);
        queryWrapper.eq(Teachplan::getParentid, parentid);
        return Math.toIntExact(teachplanMapper.selectCount(queryWrapper));
    }

}




