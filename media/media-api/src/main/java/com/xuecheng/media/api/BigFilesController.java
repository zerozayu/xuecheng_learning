package com.xuecheng.media.api;

import com.xuecheng.base.model.RestResponse;
import com.xuecheng.media.model.dto.UploadFileParamsDto;
import com.xuecheng.media.service.MediaFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 大文件上传接口
 *
 * @author zhangyu
 * @date 2024/1/29 21:57
 */
@Api(value = "大文件上传接口", tags = "大文件上传接口")
@RestController
public class BigFilesController {

    private final MediaFilesService mediaFilesService;

    public BigFilesController(MediaFilesService mediaFilesService) {
        this.mediaFilesService = mediaFilesService;
    }

    @ApiOperation(value = "上传文件前检查文件")
    @PostMapping("/upload/checkfile")
    public RestResponse<Boolean> checkFile(@RequestParam(value = "fileMd5") String fileMd5) {
        return mediaFilesService.checkFile(fileMd5);

    }

    @ApiOperation(value = "分块文件上传前的检测")
    @PostMapping("/upload/checkchunk")
    public RestResponse<Boolean> checkChunk(@RequestParam(value = "fileMd5") String fileMd5,
                                            @RequestParam(value = "chunk") int chunk) {
        return mediaFilesService.checkChunk(fileMd5, chunk);
    }

    @ApiOperation(value = "上传分块文件")
    @PostMapping("/upload/uploadchunk")
    public RestResponse uploadChunk(@RequestParam(value = "file") MultipartFile file,
                                    @RequestParam(value = "fileMd5") String fileMd5,
                                    @RequestParam(value = "chunk") int chunk) throws IOException {
        // 创建临时文件
        File tempFile = File.createTempFile("minio", "temp");
        // 上传的文件拷贝到临时文件
        file.transferTo(tempFile);
        // 文件路径
        String absolutePath = tempFile.getAbsolutePath();
        return mediaFilesService.uploadChunk(fileMd5, chunk, absolutePath);
    }

    @ApiOperation(value = "合并文件")
    @PostMapping("/upload/mergechunks")
    public RestResponse mergeChunks(@RequestParam(value = "fileMd5") String fileMd5,
                                    @RequestParam(value = "fileName") String fileName,
                                    @RequestParam(value = "chunkTotal") int chunkTotal) {
        Long companyId = 1232141425L;
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFileType("001002");
        uploadFileParamsDto.setTags("课程视频");
        uploadFileParamsDto.setRemark("");
        uploadFileParamsDto.setFilename(fileName);

        return mediaFilesService.mergeChunk(companyId, fileMd5, chunkTotal, uploadFileParamsDto);

    }
}
