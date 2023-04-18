package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author zhangyu
* @description 针对表【course_category(课程分类)】的数据库操作Service实现
* @createDate 2023-04-18 23:49:43
*/
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory>
    implements CourseCategoryService{

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
       return courseCategoryMapper.selectTreeNodes(id);
    }

}




