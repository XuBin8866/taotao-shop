package com.taotao.common.util;


import com.sun.org.apache.bcel.internal.generic.NEW;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author xxbb
 */
public class ImageUtil {

   public static String storeImage(CommonsMultipartFile file){
       String basePath=PathUtil.getImageBasePath()+PathUtil.getItemCategoryImagePath();
       String path=basePath+getRandomFileName()+getFileExtension(file);
       System.out.println(path);
       File newFile= new File(path);
       System.out.println(newFile.getAbsolutePath());
       try {
           file.transferTo(newFile);
       } catch (IOException e) {
           throw new RuntimeException(e.getMessage());
       }
       //将操作系统的分隔符形式转化为http形式的分隔符，方便直接访问图片
       return path.replace("\\", "/");
   }

    /**
     * 获取输入 文件流的扩展名
     * @param  file 输入文件
     * @return 扩展名
     */
    private static String getFileExtension(CommonsMultipartFile file) {
        String originalFileName=file.getOriginalFilename();
        assert originalFileName != null;
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名 当前时间（到秒）+五位随机数
     * @return 随机文件名
     */
    private static String getRandomFileName() {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        int randomNum= new Random().nextInt(89999)+10000;
        String nowTimeStr=simpleDateFormat.format(new Date());
        return nowTimeStr+randomNum;
    }
}
