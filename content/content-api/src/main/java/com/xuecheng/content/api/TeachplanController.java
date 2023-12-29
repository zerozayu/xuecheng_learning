package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.service.TeachplanService;
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
    private final TeachplanService teachplanService;

    public TeachplanController(TeachplanService teachplanService) {
        this.teachplanService = teachplanService;
    }

    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId", name = "课程Id", required = true, dataType = "String", paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable String courseId) {
        return teachplanService.findTeachplanTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachPlan(@RequestBody SaveTeachplanDto teachplanDto) {
        teachplanService.saveTeachpan(teachplanDto);
    }

    @ApiOperation("课程计划删除")
    @ApiImplicitParam(value = "teachPlanId", name = "课程计划id", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/teachplan/{teachPlanId}")
    public void removaTeachPlan(@PathVariable("teachPlanId") String teachPlanId) {
        teachplanService.removeTeachPlan(teachPlanId);
    }
}
