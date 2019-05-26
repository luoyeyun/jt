package com.jt.manage.service.impl;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class PicServiceImpl implements PicService {
    @Value("${image.localpath}")
    private String localpath;
    @Value("${image.urlpath}")
    private String urlpath;
    //test1

    /**
     * 文件上传实现思路
     * 1.校验文件类型 jpg|png|gif....
     * 2.校验是否为恶意程序
     * 3.为了防止图片检索速度慢,采用分文件存储  yyyy/MM/dd/
     * 4.防止文件重名  UUID + 随机数(3)
     * 5.实现文件上传
     *
     */
    @Override
    public PicUploadResult upload(MultipartFile uploadFile) {
        PicUploadResult result = new PicUploadResult();
        //1.校验文件类型
        String fileName = uploadFile.getOriginalFilename();
        fileName = fileName.toLowerCase();
        if (!fileName.matches("^.+\\.(jpg|png|gif)$")){
            result.setError(1);
            return result;
        }

        //2.校验上传文件是不是恶意程序
        try {
            //读取文件，如果是图片则有宽度及高度
            BufferedImage image = ImageIO.read(uploadFile.getInputStream());
            int width = image.getWidth();
            int height = image.getHeight();
            if (width==0 || height==0){
                result.setError(1);
                return result;
            }

            //3.为了防止图片检索速度慢,采用分文件存储  yyyy/MM/dd/
            String formatDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            String localPathDir = localpath + formatDate;

            //判断文件夹是否存在
            File fileDir = new File(localPathDir);
            if (!fileDir.exists()){
                fileDir.mkdirs();
            }

            //4.定义文件名称 防止文件重名  UUID + 随机数(3)
            String uuid = UUID.randomUUID().toString().replace("-", "");
            int ran = new Random().nextInt(1000);

            //文件类型
            String fileType = fileName.substring(fileName.lastIndexOf('.'));

            //形成文件名称
            String realName = uuid + ran +fileType;

            String localPathReal = localPathDir+"/"+realName;

            //文件上传
            uploadFile.transferTo(new File(localPathReal));

            //定义图片回显路径
            //String url = "https://img14.360buyimg.com/n0/jfs/t1/7301/36/10557/363153/5c231de0E0a5565dd/2e8054392374dc29.jpg";
            String url = urlpath + formatDate + "/" + realName;
            result.setUrl(url);


        } catch (IOException e) {
            e.printStackTrace();
            result.setError(1);
            return result;
        }

        return result;
    }
}
