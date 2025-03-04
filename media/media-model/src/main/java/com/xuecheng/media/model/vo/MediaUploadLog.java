package com.xuecheng.media.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件上传记录表
 *
 * @author zhangyu
 * @date 2024/2/29 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MediaUploadLog implements Serializable {

    private String id;

    private String bucket;

    private String fileStatus;

    private LocalDateTime createTime;
}
