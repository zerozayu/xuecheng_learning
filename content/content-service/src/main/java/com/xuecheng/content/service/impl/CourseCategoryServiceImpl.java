package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyu
 * @description 针对表【course_category(课程分类)】的数据库操作Service实现
 * @createDate 2023-04-18 23:49:43
 */
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        this.removeEmptyNode(courseCategoryTreeDtos);

        return courseCategoryTreeDtos;
    }

    /**
     * 若子节点数组为空列表,则置为空
     *
     * @param courseCategoryTreeDtos
     */
    private void removeEmptyNode(List<CourseCategoryTreeDto> courseCategoryTreeDtos) {

        for (CourseCategoryTreeDto courseCategoryTreeDto : courseCategoryTreeDtos) {
            if (CollectionUtils.isNotEmpty(courseCategoryTreeDto.getChildrenTreeNodes())) {
                this.removeEmptyNode(courseCategoryTreeDto.getChildrenTreeNodes());
            } else {
                courseCategoryTreeDto.setChildrenTreeNodes(null);
            }
        }
    }

}




