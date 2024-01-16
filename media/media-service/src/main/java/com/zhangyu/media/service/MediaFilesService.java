package com.zhangyu.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangyu.media.model.dto.UploadFileParamsDto;
import com.zhangyu.media.model.dto.UploadFileResultDto;
import com.zhangyu.media.model.vo.MediaFiles;

/**
 * @author zhangyu
 * @description 针对表【media_files(媒资信息)】的数据库操作Service
 * @createDate 2024-01-16 22:00:11
 */
public interface MediaFilesService extends IService<MediaFiles> {

    /**
     * 上传文件
     *
     * @param companyId           机构id
     * @param uploadFileParamsDto 上传文件信息
     * @param localFilePath       文件磁盘路径
     * @return 文件信息
     */
    UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);
}
