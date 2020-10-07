package com.taotao.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author xxbb
 */
public class PathUtil {
    private static final String SEPARATOR=System.getProperty("file.separator");
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 指定文件保存的根目录
     * @return 路径
     */
    public static String getImageBasePath(){
        String os=System.getProperty("os.name");
        String basePath;
        if(os.toLowerCase().startsWith("win")){
            basePath="D:/taotao-shop-lib/image";
        }else{
            basePath="/home/taotao-shop-lib/image";
        }
        //全平台分隔符适配
        basePath=basePath.replace("/",SEPARATOR);
        return basePath;
    }

    public static String getItemCategoryImagePath() {
        String shopCategoryImagePath = "/upload/images/item/item-category/";
        shopCategoryImagePath = shopCategoryImagePath.replace("/", SEPARATOR);
        return shopCategoryImagePath;
    }


    public static String getRandomFileName() {
        // 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
        // 获取随机数
        int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000;
        // 当前时间
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    public static void deleteFile(String storePath) {
        File file = new File(getImageBasePath() + storePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            file.delete();
        }
    }
}
