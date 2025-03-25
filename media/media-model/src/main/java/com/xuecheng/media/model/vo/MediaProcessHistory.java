package com.xuecheng.media.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;

/**
 * @TableName media_process_history
 */
@TableName(value = "media_process_history")
@Data
public class MediaProcessHistory {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件标识
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 存储源
     */
    private String bucket;

    /**
     * 状态,1:未处理，2：处理成功  3处理失败
     */
    private String status;

    /**
     * 上传时间
     */
    private Date createDate;

    /**
     * 完成时间
     */
    private Date finishDate;

    /**
     * 媒资文件访问地址
     */
    private String url;

    /**
     * 失败次数
     */
    private Integer failCount;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 失败原因
     */
    private String errormsg;
}