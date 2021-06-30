package com.learn.minio;

import io.minio.*;
import io.minio.http.Method;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Minio 外链测试：
 * 创建上传、下载外链；设置外链策略
 */
public class MinioPresignedTest {
    private static MinioClient minioClient;

    static {
        minioClient = new MinioClient.Builder()
                .endpoint(BaseConstants.MinioClient.IP, BaseConstants.MinioClient.PORT, false)
                .credentials(BaseConstants.MinioClient.ACCESS_KEY, BaseConstants.MinioClient.SECRET_KEY)
                .build();
    }

    public static void main(String[] args) {
        getPresignedObjectUrl(Method.GET, "learn", "aaa.txt", 60 * 60 * 24);
    }

    /**
     * 获取文件外链
     * @param method 外链的方式：
     *               删除：Method.DELETE
     *               下载：Method.GET
     *               上传：Method.PUT
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expiry 失效时间（以秒为单位），默认是7天，不得大于七天
     */
    public static void getPresignedObjectUrl(Method method, String bucketName, String objectName, Integer expiry){
        System.out.println("----------获取文件外链----------");
        try {
            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                    .method(method)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(expiry)
                    .build();
            String str = minioClient.getPresignedObjectUrl(args);
            System.out.println("存储桶["+bucketName+"]中对象["+objectName+"]的外链为："+str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
