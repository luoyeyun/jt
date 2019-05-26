package com.jt.manage.service;

import com.jt.common.vo.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface PicService {
    PicUploadResult upload(MultipartFile uploadFile);
}
