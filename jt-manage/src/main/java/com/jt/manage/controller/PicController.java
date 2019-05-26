package com.jt.manage.controller;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 富文本文件上传
 */
@Controller
public class PicController {
    @Autowired
    private PicService pciService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public PicUploadResult upload(MultipartFile uploadFile){

        return pciService.upload(uploadFile);
    }
}
