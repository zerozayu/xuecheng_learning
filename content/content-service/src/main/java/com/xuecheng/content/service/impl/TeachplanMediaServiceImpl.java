package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanMediaService;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyu
 * @description 针对表【teachplan_media】的数据库操作Service实现
 * @createDate 2023-12-06 23:31:46
 */
@Service
public class TeachplanMediaServiceImpl extends ServiceImpl<TeachplanMediaMapper, TeachplanMedia>
        implements TeachplanMediaService {

    private final TeachplanMapper teachplanMapper;

    public TeachplanMediaServiceImpl(TeachplanMapper teachplanMapper) {
        this.teachplanMapper = teachplanMapper;
    }

    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }
}




