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
            teachplanMapper.updateById(teachplan);
        } else {
            // 取出同父同级别的课程计划数量
            int count = this.getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            teachplanNew.setOrderby(count + 1);
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




