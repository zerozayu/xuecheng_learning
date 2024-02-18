package com.xuecheng.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.RestResponse;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileParamsDto;
import com.xuecheng.media.model.dto.UploadFileResultDto;
import com.xuecheng.media.model.vo.MediaFiles;
import io.swagger.models.auth.In;

/**
 * @author zhangyu
 * @description 针对表【media_files(媒资信息)】的数据库操作Service
 * @createDate 2024-01-16 22:00:11
 */
public interface MediaFilesService extends IService<MediaFiles> {

    PageResult<MediaFiles> queryMediaFiles(PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

    /**
     * 上传文件
     *
     * @param companyId           机构id
     * @param uploadFileParamsDto 上传文件信息
     * @param localFilePath       文件磁盘路径
     * @return 文件信息
     */
    UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

    /**
     * 将文件信息添加到文件表
     *
     * @param companyId           机构id
     * @param fileMd5             文件md5值
     * @param uploadFileParamsDto 文件上传信息
     * @param bucket              桶
     * @param objectName          对象名
     * @return {@link MediaFiles}
     * @throws
     * @author zhangyu
     * @date 2024/1/16 23:53
     */
    MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);

    /**
     * 检查文件是否存在
     *
     * @param fileMd5 文件md5值
     * @return {@link RestResponse<Boolean>}
     * @throws
     * @author zhangyu
     * @date 2024/1/29 22:13
     */
    RestResponse<Boolean> checkFile(String fileMd5);

    /**
     * 检查分块是否存在
     *
     * @param fileMd5    文件的md5值
     * @param chunkIndex 分块序号
     * @return {@link RestResponse<Boolean>}
     * @throws
     * @author zhangyu
     * @date 2024/1/29 22:14
     */
    RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    RestResponse uploadChunk(String fileMd5, int chunk, String localFilePath);

    RestResponse mergeChunk(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto);
}
