package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanMediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程计划编辑接口
 *
 * @author zhangyu
 * @date 2023/12/6 22:38
 */
@Api(value = "课程计划编辑接口", tags = "课程计划编辑接口1")
@RestController
public class TeachplanController {
    private final TeachplanMediaService teachplanMediaService;

    public TeachplanController(TeachplanMediaService teachplanMediaService) {
        this.teachplanMediaService = teachplanMediaService;
    }

    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId", name = "课程Id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachplanMediaService.findTeachplanTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachPlan(@RequestBody SaveTeachplanDto teachplanDto) {
        teachplanMediaService.saveTeachpan(teachplanDto);
    }
}
