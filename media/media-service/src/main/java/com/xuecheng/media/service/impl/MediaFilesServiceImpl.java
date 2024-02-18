package com.xuecheng.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.RestResponse;
import com.xuecheng.media.mapper.MediaFilesMapper;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileParamsDto;
import com.xuecheng.media.model.dto.UploadFileResultDto;
import com.xuecheng.media.model.vo.MediaFiles;
import com.xuecheng.media.service.MediaFilesService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangyu
 * @description 针对表【media_files(媒资信息)】的数据库操作Service实现
 * @createDate 2024-01-16 22:00:11
 */
@Log4j2
@Service
public class MediaFilesServiceImpl extends ServiceImpl<MediaFilesMapper, MediaFiles>
        implements MediaFilesService {

    //普通文件桶
    @Value("${minio.bucket.files}")
    private String bucket_files;
    // 视频文件桶
    @Value("${minio.bucket.videofiles}")
    private String bucket_videoFiles;

    private final MinioClient minioClient;
    private final MediaFilesMapper mediaFilesMapper;

    public MediaFilesServiceImpl(MinioClient minioClient, MediaFilesMapper mediaFilesMapper) {
        this.minioClient = minioClient;
        this.mediaFilesMapper = mediaFilesMapper;
    }

    @Override
    public PageResult<MediaFiles> queryMediaFiles(PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {

        // 分页对象
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        //构建查询条件对象
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();
        // 查询数据内容获得结果
        Page<MediaFiles> pageResult = mediaFilesMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<MediaFiles> list = pageResult.getRecords();
        // 获取数据总数
        long total = page.getTotal();
        // 构建结果集
        PageResult<MediaFiles> mediaListResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
        return mediaListResult;
    }

    @Transactional
    @Override
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) {
        File file = new File(localFilePath);
        if (!file.exists()) {
            XueChengPlusException.cast("文件不存在");
        }

        // 文件名称
        String filename = uploadFileParamsDto.getFilename();
        // 文件扩展名
        String extension = filename.substring(filename.lastIndexOf("."));
        String mimeType = getMimeType(extension);
        //文件的md5值
        String fileMd5 = getFileMd5(file);
        //文件的默认目录
        String defaultFolderPath = getDefaultFolderPath();
        //存储到minio中的对象名(带目录)
        String objectName = defaultFolderPath + fileMd5 + extension;
        //将文件上传到minio
        boolean b = addMediaFilesToMinIO(localFilePath, mimeType, bucket_files, objectName);
        //文件大小
        uploadFileParamsDto.setFileSize(file.length());
        //将文件信息存储到数据库
        MediaFiles mediaFiles = ((MediaFilesService) AopContext.currentProxy()).addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_files, objectName);
        //准备返回数据
        UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
        BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);
        return uploadFileResultDto;

    }

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
    @Transactional
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName) {
        //从数据库查询文件
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles == null) {
            mediaFiles = new MediaFiles();
            //拷贝基本信息
            BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
            mediaFiles.setId(fileMd5);
            mediaFiles.setFileId(fileMd5);
            mediaFiles.setCompanyId(companyId);
            mediaFiles.setUrl("/" + bucket + "/" + objectName);
            mediaFiles.setBucket(bucket);
            mediaFiles.setFilePath(objectName);
            mediaFiles.setCreateDate(LocalDateTime.now());
            mediaFiles.setAuditStatus("002003");
            mediaFiles.setStatus("1");
            //保存文件信息到文件表
            int insert = mediaFilesMapper.insert(mediaFiles);
            if (insert < 0) {
                log.error("保存文件信息到数据库失败,{}", mediaFiles.toString());
                XueChengPlusException.cast("保存文件信息失败");
            }
            log.debug("保存文件信息到数据库成功,{}", mediaFiles.toString());

        }
        return mediaFiles;
    }

    @Override
    public RestResponse<Boolean> checkFile(String fileMd5) {
        // 查询文件信息
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileMd5);
        if (mediaFiles != null) {
            // 桶
            String bucket = mediaFiles.getBucket();
            // 存储目录
            String filePath = mediaFiles.getFilePath();
            InputStream stream = null;
            try {
                stream = minioClient.getObject(GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(filePath)
                        .build());
                if (stream != null) {
                    return RestResponse.success(true);
                }
            } catch (Exception e) {

            }
        }
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex) {
        // 得到分块文件目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        // 得到分块文件的路径
        String chunkFilePath = chunkFileFolderPath + chunkIndex;

        // 文件流
        InputStream stream = null;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket_videoFiles)
                    .object(chunkFilePath)
                    .build());
            if (stream != null) {
                // 分块已经存在
                return RestResponse.success(true);
            }
        } catch (Exception e) {

        }
        return RestResponse.success(false);
    }

    @Override
    public RestResponse uploadChunk(String fileMd5, int chunk, String localFilePath) {

        // 得到分块文件的目录路径
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        String chunkFilePath = chunkFileFolderPath + chunk;

        try {
            // 将文件存储至 MinIO
            addMediaFilesToMinIO(localFilePath, MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE, bucket_videoFiles, chunkFilePath);
            return RestResponse.success(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("上传分块文件:{},失败:{}", chunkFilePath, e.getMessage());
        }
        return RestResponse.validfail("上传文件分块失败", false);
    }

    @Override
    public RestResponse mergeChunk(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) {
        // ========获取分块文件路径==========
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        // 将分块文件路径组成 List<ComposeSource>
        List<ComposeSource> sourceObjectList = Stream.iterate(0, i -> ++i)
                .limit(chunkTotal)
                .map(i -> ComposeSource.builder()
                        .bucket(bucket_videoFiles)
                        .object(chunkFileFolderPath.concat(Integer.toString(i)))
                        .build())
                .collect(Collectors.toList());
        // ========合并==========
        // 文件
        String fileName = uploadFileParamsDto.getFilename();
        // 文件扩展名
        String extName = fileName.substring(fileName.lastIndexOf("."));
        // 合并文件路径
        String mergeFilePath = getFilePathByMd5(fileName, extName);
        try {
            // 合并文件
            ObjectWriteResponse response = minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(bucket_videoFiles)
                            .object(mergeFilePath)
                            .sources(sourceObjectList)
                            .build()
            );
            log.debug("文件合并成功:{}", mergeFilePath);
        } catch (Exception e) {
            log.debug("合并文件失败, fileMd5:{}, 异常:{}", fileMd5, e.getMessage(), e);
            return RestResponse.validfail("文件合并失败.", false);
        }

        // =======验证md5=======
        File minioFile = downloadFileFromMinIO(bucket_videoFiles, mergeFilePath);
        if (minioFile == null) {
            log.debug("下载合并后文件失败, mergeFilePath: {}", mergeFilePath);
            return RestResponse.validfail("下载合并后文件失败.", false);
        }

        try (InputStream newFileInputStream = new FileInputStream(minioFile)) {
            // minio上文件的md5值
            String md5Hex = DigestUtils.md5Hex(newFileInputStream);
            // 比较md5值
            if (!fileMd5.equals(md5Hex)) {
                return RestResponse.validfail("文件合并校验失败,最终上传失败.", false);
            }
            // 文件大小
            uploadFileParamsDto.setFileSize(minioFile.length());
        } catch (Exception e) {
            log.debug("校验文件失败, fileMd5:{}, 异常:{}", fileMd5, e.getMessage(), e);
            return RestResponse.validfail("文件合并校验失败,最终上传失败.", false);
        } finally {
            if (minioFile != null) {
                minioFile.delete();
            }
        }

        // 文件入库
        ((MediaFilesService) AopContext.currentProxy()).addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucket_videoFiles, mergeFilePath);

        // 清除分块文件
        clearChunkFiles(chunkFileFolderPath, chunkTotal);

        return RestResponse.success(true);
    }

    private void clearChunkFiles(String chunkFileFolderPath, int chunkTotal) {
        try {
            List<DeleteObject> deleteObjects = Stream.iterate(0, i -> ++i)
                    .limit(chunkTotal)
                    .map(i -> new DeleteObject(chunkFileFolderPath.concat(Integer.toString(i))))
                    .collect(Collectors.toList());

            RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder()
                    .bucket(bucket_videoFiles)
                    .objects(deleteObjects)
                    .build();

            Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
            results.forEach(r -> {
                DeleteError deleteError = null;
                try {
                    deleteError = r.get();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("清除分块文件失败, objectName:{}", deleteError.objectName(), e);
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            log.error("清除分块文件失败, chunkFileFolderPath:{}", chunkFileFolderPath, e);
        }
    }

    /**
     * 从minio下载文件
     *
     * @param bucket     桶
     * @param objectName 对象名称
     * @return {@link File} 下载后的文件
     * @throws
     * @author zhangyu
     * @date 2024/2/18 22:31
     */
    private File downloadFileFromMinIO(String bucket, String objectName) {
        // 临时文件
        File minioFile = null;
        FileOutputStream outputStream = null;
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
            // 创建临时文件
            minioFile = File.createTempFile("minio", ".merge");
            outputStream = new FileOutputStream(minioFile);
            IOUtils.copy(inputStream, outputStream);
            return minioFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 得到合并后的文件的地址
     *
     * @param fileMd5 文件id即md5值
     * @param fileExt 文件扩展名
     * @return {@link String}
     * @throws
     * @author zhangyu
     * @date 2024/2/18 22:32
     */
    private String getFilePathByMd5(String fileMd5, String fileExt) {
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/" + fileMd5 + fileExt;
    }

    private String getChunkFileFolderPath(String fileMd5) {
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/chunk/";
    }

    /**
     * 将文件写入minIO
     *
     * @param localFilePath 文件地址
     * @param mimeType      mimeType
     * @param bucket        桶
     * @param objectName    对象名称
     * @return void
     */
    private boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .filename(localFilePath)
                    .contentType(mimeType)
                    .build();
            minioClient.uploadObject(uploadObjectArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件到minio出错,bucket:{},objectName:{},错误原因:{}", bucket, objectName, e.getMessage(), e);
            XueChengPlusException.cast("上传文件到文件系统失败");
        }
        return false;
    }

    //获取文件默认存储目录路径 年/月/日/
    private String getDefaultFolderPath() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date()).replace("-", "/") + "/";
    }

    // 获取文件的md5值
    private String getFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getMimeType(String extension) {
        if (extension == null) {
            extension = "";
        }
        // 根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }
}




