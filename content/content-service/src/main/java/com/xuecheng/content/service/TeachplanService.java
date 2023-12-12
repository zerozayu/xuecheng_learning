package com.xuecheng.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;

import java.util.List;

/**
* @author zhangyu
* @description 针对表【teachplan(课程计划)】的数据库操作Service
* @createDate 2023-12-06 23:31:40
*/
public interface TeachplanService extends IService<Teachplan> {

    List<TeachplanDto> findTeachplanTree(Long courseId);

    void saveTeachpan(SaveTeachplanDto teachplanDto);
}
