package com.xuecheng.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 分页查询通用参数
 *
 * @author zhangyu
 * @date 2023/4/14 16:11
 */
@Data
@ToString
public class PageParams {

    //当前页码
    @ApiModelProperty(name = "pageNo", value = "当前页码", example = "1", position = 1)
    private Long pageNo = 1L;

    //每页记录数默认值
    @ApiModelProperty(name = "pageSize", value = "每页记录数", example = "10", position = 2)
    private Long pageSize = 10L;

    public PageParams() {

    }

    public PageParams(long pageNo, long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

}
