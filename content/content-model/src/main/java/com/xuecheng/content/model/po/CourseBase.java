package com.xuecheng.content.model.po;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 课程基本信息
* @TableName course_base
*/
public class CourseBase implements Serializable {

    /**
    * 主键
    */
    @NotNull(message="[主键]不能为空")
    // @ApiModelProperty("主键")
    private Long id;
    /**
    * 机构ID
    */
    @NotNull(message="[机构ID]不能为空")
    // @ApiModelProperty("机构ID")
    private Long company_id;
    /**
    * 机构名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    // @ApiModelProperty("机构名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String company_name;
    /**
    * 课程名称
    */
    @NotBlank(message="[课程名称]不能为空")
    @Size(max= 100,message="编码长度不能超过100")
    // @ApiModelProperty("课程名称")
    @Length(max= 100,message="编码长度不能超过100")
    private String name;
    /**
    * 适用人群
    */
    @Size(max= 500,message="编码长度不能超过500")
    // @ApiModelProperty("适用人群")
    @Length(max= 500,message="编码长度不能超过500")
    private String users;
    /**
    * 课程标签
    */
    @Size(max= 50,message="编码长度不能超过50")
    // @ApiModelProperty("课程标签")
    @Length(max= 50,message="编码长度不能超过50")
    private String tags;
    /**
    * 大分类
    */
    @NotBlank(message="[大分类]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    // @ApiModelProperty("大分类")
    @Length(max= 20,message="编码长度不能超过20")
    private String mt;
    /**
    * 小分类
    */
    @NotBlank(message="[小分类]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    // @ApiModelProperty("小分类")
    @Length(max= 20,message="编码长度不能超过20")
    private String st;
    /**
    * 课程等级
    */
    @NotBlank(message="[课程等级]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    // @ApiModelProperty("课程等级")
    @Length(max= 32,message="编码长度不能超过32")
    private String grade;
    /**
    * 教育模式(common普通，record 录播，live直播等）
    */
    @NotBlank(message="[教育模式(common普通，record 录播，live直播等）]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    // @ApiModelProperty("教育模式(common普通，record 录播，live直播等）")
    @Length(max= 32,message="编码长度不能超过32")
    private String teachmode;
    /**
    * 课程介绍
    */
    @Size(max= -1,message="编码长度不能超过-1")
    // @ApiModelProperty("课程介绍")
    @Length(max= -1,message="编码长度不能超过-1")
    private String description;
    /**
    * 课程图片
    */
    @Size(max= 500,message="编码长度不能超过500")
    // @ApiModelProperty("课程图片")
    @Length(max= 500,message="编码长度不能超过500")
    private String pic;
    /**
    * 创建时间
    */
    // @ApiModelProperty("创建时间")
    private LocalDateTime create_date;
    /**
    * 修改时间
    */
    // @ApiModelProperty("修改时间")
    private LocalDateTime change_date;
    /**
    * 创建人
    */
    @Size(max= 50,message="编码长度不能超过50")
    // @ApiModelProperty("创建人")
    @Length(max= 50,message="编码长度不能超过50")
    private String create_people;
    /**
    * 更新人
    */
    @Size(max= 50,message="编码长度不能超过50")
    // @ApiModelProperty("更新人")
    @Length(max= 50,message="编码长度不能超过50")
    private String change_people;
    /**
    * 审核状态
    */
    @NotBlank(message="[审核状态]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    // @ApiModelProperty("审核状态")
    @Length(max= 10,message="编码长度不能超过10")
    private String audit_status;
    /**
    * 课程发布状态 未发布  已发布 下线
    */
    @NotBlank(message="[课程发布状态 未发布  已发布 下线]不能为空")
    @Size(max= 10,message="编码长度不能超过10")
    // @ApiModelProperty("课程发布状态 未发布  已发布 下线")
    @Length(max= 10,message="编码长度不能超过10")
    private String status;

    /**
    * 主键
    */
    private void setId(Long id){
    this.id = id;
    }

    /**
    * 机构ID
    */
    private void setCompany_id(Long company_id){
    this.company_id = company_id;
    }

    /**
    * 机构名称
    */
    private void setCompany_name(String company_name){
    this.company_name = company_name;
    }

    /**
    * 课程名称
    */
    private void setName(String name){
    this.name = name;
    }

    /**
    * 适用人群
    */
    private void setUsers(String users){
    this.users = users;
    }

    /**
    * 课程标签
    */
    private void setTags(String tags){
    this.tags = tags;
    }

    /**
    * 大分类
    */
    private void setMt(String mt){
    this.mt = mt;
    }

    /**
    * 小分类
    */
    private void setSt(String st){
    this.st = st;
    }

    /**
    * 课程等级
    */
    private void setGrade(String grade){
    this.grade = grade;
    }

    /**
    * 教育模式(common普通，record 录播，live直播等）
    */
    private void setTeachmode(String teachmode){
    this.teachmode = teachmode;
    }

    /**
    * 课程介绍
    */
    private void setDescription(String description){
    this.description = description;
    }

    /**
    * 课程图片
    */
    private void setPic(String pic){
    this.pic = pic;
    }

    /**
    * 创建时间
    */
    private void setCreate_date(LocalDateTime create_date){
    this.create_date = create_date;
    }

    /**
    * 修改时间
    */
    private void setChange_date(LocalDateTime change_date){
    this.change_date = change_date;
    }

    /**
    * 创建人
    */
    private void setCreate_people(String create_people){
    this.create_people = create_people;
    }

    /**
    * 更新人
    */
    private void setChange_people(String change_people){
    this.change_people = change_people;
    }

    /**
    * 审核状态
    */
    private void setAudit_status(String audit_status){
    this.audit_status = audit_status;
    }

    /**
    * 课程发布状态 未发布  已发布 下线
    */
    private void setStatus(String status){
    this.status = status;
    }


    /**
    * 主键
    */
    private Long getId(){
    return this.id;
    }

    /**
    * 机构ID
    */
    private Long getCompany_id(){
    return this.company_id;
    }

    /**
    * 机构名称
    */
    private String getCompany_name(){
    return this.company_name;
    }

    /**
    * 课程名称
    */
    private String getName(){
    return this.name;
    }

    /**
    * 适用人群
    */
    private String getUsers(){
    return this.users;
    }

    /**
    * 课程标签
    */
    private String getTags(){
    return this.tags;
    }

    /**
    * 大分类
    */
    private String getMt(){
    return this.mt;
    }

    /**
    * 小分类
    */
    private String getSt(){
    return this.st;
    }

    /**
    * 课程等级
    */
    private String getGrade(){
    return this.grade;
    }

    /**
    * 教育模式(common普通，record 录播，live直播等）
    */
    private String getTeachmode(){
    return this.teachmode;
    }

    /**
    * 课程介绍
    */
    private String getDescription(){
    return this.description;
    }

    /**
    * 课程图片
    */
    private String getPic(){
    return this.pic;
    }

    /**
    * 创建时间
    */
    private LocalDateTime getCreate_date(){
    return this.create_date;
    }

    /**
    * 修改时间
    */
    private LocalDateTime getChange_date(){
    return this.change_date;
    }

    /**
    * 创建人
    */
    private String getCreate_people(){
    return this.create_people;
    }

    /**
    * 更新人
    */
    private String getChange_people(){
    return this.change_people;
    }

    /**
    * 审核状态
    */
    private String getAudit_status(){
    return this.audit_status;
    }

    /**
    * 课程发布状态 未发布  已发布 下线
    */
    private String getStatus(){
    return this.status;
    }

}
