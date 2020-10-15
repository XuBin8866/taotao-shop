package com.taotao.controller;

import com.taotao.common.util.ImageUtil;
import com.taotao.common.util.PathUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.HashMap;
import java.util.Map;


/**
 * 文件上传
 *
 * @author xxbb
 */
@Controller
public class PictureController {

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map<String, Object> fileUpload(MultipartFile uploadFile) {
        //存储图片

        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            String url = ImageUtil.storeImage((CommonsMultipartFile) uploadFile);
            resultMap.put("url", url);
            resultMap.put("error", 0);
            return resultMap;
        } catch (Exception e) {
            resultMap.put("error",1);
            resultMap.put("message","图片上传失败");
            System.out.println(e.getMessage());
            return resultMap;
        }
    }
}
