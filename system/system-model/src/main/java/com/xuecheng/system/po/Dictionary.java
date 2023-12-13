package com.xuecheng.system.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 数据字典
 *
 * @TableName dictionary
 */
@Data
public class Dictionary implements Serializable {

    /**
     * id标识
     */
    @NotNull(message = "[id标识]不能为空")
    @ApiModelProperty("id标识")
    private String id;
    /**
     * 数据字典名称
     */
    @Size(max = 32, message = "编码长度不能超过32")
    @ApiModelProperty("数据字典名称")
    @Length(max = 32, message = "编码长度不能超过32")
    private String name;
    /**
     * 数据字典代码
     */
    @Size(max = 32, message = "编码长度不能超过32")
    @ApiModelProperty("数据字典代码")
    @Length(max = 32, message = "编码长度不能超过32")
    private String code;
    /**
     * 数据字典项--json格式
     */
    @Size(max = -1, message = "编码长度不能超过-1")
    @ApiModelProperty("数据字典项--json格式")
    @Length(max = -1, message = "编码长度不能超过-1")
    private String itemValues;
}
