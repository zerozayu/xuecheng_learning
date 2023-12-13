package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;

import java.util.List;

/**
 * @author zhangyu
 * @description 针对表【teachplan(课程计划)】的数据库操作Mapper
 * @createDate 2023-12-06 23:31:40
 * @Entity com.xuecheng.content.domain.Teachplan
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {

    List<TeachplanDto> selectTreeNodes(String courseId);
}




