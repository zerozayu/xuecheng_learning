package com.zhangyu.media.model.dto;

import com.zhangyu.media.model.vo.MediaFiles;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上传文件
 *
 * @author zhangyu
 * @date 2024/1/16 21:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UploadFileResultDto extends MediaFiles {
}
