package com.xuecheng.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.media.mapper.MediaUploadLogMapper;
import com.xuecheng.media.model.vo.MediaUploadLog;
import com.xuecheng.media.service.MediaUploadLogService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhangyu
 * @date 2024/2/29 16:37
 */
@Slf4j
@Service
public class MediaUploadLogServiceImpl extends ServiceImpl<MediaUploadLogMapper, MediaUploadLog> implements MediaUploadLogService {
}
