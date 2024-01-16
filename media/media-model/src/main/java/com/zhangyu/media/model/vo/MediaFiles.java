package com.zhangyu.media.model.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 媒资信息
* @TableName media_files
*/
@Data
public class MediaFiles implements Serializable {

    /**
    * 文件id,md5值
    */
    @NotBlank(message="[文件id,md5值]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("文件id,md5值")
    @Length(max= 32,message="编码长度不能超过32")
    private String id;
    /**
    * 机构ID
    */
    @ApiModelProperty("机构ID")
    private Long companyId;
    /**
    * 机构名称
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("机构名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String companyName;
    /**
    * 文件名称
    */
    @NotBlank(message="[文件名称]不能为空")
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("文件名称")
    @Length(max= 255,message="编码长度不能超过255")
    private String filename;
    /**
    * 文件类型（图片、文档，视频）
    */
    @Size(max= 12,message="编码长度不能超过12")
    @ApiModelProperty("文件类型（图片、文档，视频）")
    @Length(max= 12,message="编码长度不能超过12")
    private String fileType;
    /**
    * 标签
    */
    @Size(max= 120,message="编码长度不能超过120")
    @ApiModelProperty("标签")
    @Length(max= 120,message="编码长度不能超过120")
    private String tags;
    /**
    * 存储目录
    */
    @Size(max= 128,message="编码长度不能超过128")
    @ApiModelProperty("存储目录")
    @Length(max= 128,message="编码长度不能超过128")
    private String bucket;
    /**
    * 存储路径
    */
    @Size(max= 512,message="编码长度不能超过512")
    @ApiModelProperty("存储路径")
    @Length(max= 512,message="编码长度不能超过512")
    private String filePath;
    /**
    * 文件id
    */
    @NotBlank(message="[文件id]不能为空")
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("文件id")
    @Length(max= 32,message="编码长度不能超过32")
    private String fileId;
    /**
    * 媒资文件访问地址
    */
    @Size(max= 1024,message="编码长度不能超过1024")
    @ApiModelProperty("媒资文件访问地址")
    @Length(max= 1024,message="编码长度不能超过1,024")
    private String url;
    /**
    * 上传人
    */
    @Size(max= 60,message="编码长度不能超过60")
    @ApiModelProperty("上传人")
    @Length(max= 60,message="编码长度不能超过60")
    private String username;
    /**
    * 上传时间
    */
    @ApiModelProperty("上传时间")
    private LocalDateTime createDate;
    /**
    * 修改时间
    */
    @ApiModelProperty("修改时间")
    private LocalDateTime changeDate;
    /**
    * 状态,1:正常，0:不展示
    */
    @Size(max= 12,message="编码长度不能超过12")
    @ApiModelProperty("状态,1:正常，0:不展示")
    @Length(max= 12,message="编码长度不能超过12")
    private String status;
    /**
    * 备注
    */
    @Size(max= 32,message="编码长度不能超过32")
    @ApiModelProperty("备注")
    @Length(max= 32,message="编码长度不能超过32")
    private String remark;
    /**
    * 审核状态
    */
    @Size(max= 12,message="编码长度不能超过12")
    @ApiModelProperty("审核状态")
    @Length(max= 12,message="编码长度不能超过12")
    private String auditStatus;
    /**
    * 审核意见
    */
    @Size(max= 255,message="编码长度不能超过255")
    @ApiModelProperty("审核意见")
    @Length(max= 255,message="编码长度不能超过255")
    private String auditMind;
    /**
    * 文件大小
    */
    @ApiModelProperty("文件大小")
    private Long fileSize;
}
