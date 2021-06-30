package com.learn.minio;

import io.minio.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Minio 文件测试：
 * 创建、查询、删除文件夹
 * 创建、查询、删除文件
 */
public class MinioFileTest {
    private static MinioClient minioClient;

    static {
        minioClient = new MinioClient.Builder()
                .endpoint(BaseConstants.MinioClient.IP, BaseConstants.MinioClient.PORT, false)
                .credentials(BaseConstants.MinioClient.ACCESS_KEY, BaseConstants.MinioClient.SECRET_KEY)
                .build();
    }

    public static void main(String[] args) {
        //getObject("learn", "aaa.txt");
        //putObject("learn", "D:/aaa.txt");
        //createFolder("learn", "test");
        //copyObject("learn", "aaa.txt", "learn2", "bbb.txt");
        removeObject("learn2", "bbb.txt");
    }

    /**
     * 判断对象是否存在
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     */
    public static boolean statObject(String bucketName, String objectName){
        System.out.println("----------判断对象是否存在----------");
        try {
            StatObjectArgs args = StatObjectArgs.builder().bucket(bucketName).object(objectName).build();
            minioClient.statObject(args);
            System.out.println("存储桶["+bucketName+"]中存在对象["+objectName+"]");
            return true;
        } catch (Exception e) {
            System.out.println("存储桶["+bucketName+"]不存在对象["+objectName+"]");
            return false;
        }
    }

    /**
     * 下载文件对象
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     */
    public static void getObject(String bucketName, String objectName){
        System.out.println("----------下载文件对象----------");
        if (statObject(bucketName, objectName)){
            try {
                GetObjectArgs args = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
                GetObjectResponse response = minioClient.getObject(args);
                byte[] buf = new byte[16384];
                int bytesRead;
                System.out.println("文件信息如下");
                while ((bytesRead = response.read(buf, 0, buf.length)) >= 0) {
                    System.out.println(new String(buf, 0, bytesRead));
                }
                System.out.println("文件信息读取完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件对象
     * @param bucketName 存储桶名称
     * @param filePath 文件路径
     */
    public static void putObject(String bucketName, String filePath){
        System.out.println("----------上传文件对象----------");
        try {
            File file = new File(filePath);
            InputStream in = new FileInputStream(file);
            String fileName = file.getName();
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName)
                    .object(fileName).stream(in, in.available(), -1).build();
            minioClient.putObject(putObjectArgs);
            System.out.println("文件["+fileName+"]，成功上传到存储桶["+bucketName+"]中。");
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件夹
     * @param bucketName 存储桶名称
     * @param folderName 文件夹名称
     */
    public static void createFolder(String bucketName, String folderName){
        System.out.println("----------创建文件夹----------");
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName)
                    .object(folderName+"/").stream(new ByteArrayInputStream(new byte[] {}), 0, -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            System.out.println("文件夹["+folderName+"]，创建成功。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件对象拷贝
     * @param bucketName 存储桶名称
     * @param objectName 源存储桶中的源对象名称
     * @param srcBucketName 目标bucket名称
     * @param srcObjectName 目标文件名称
     */
    public static void copyObject(String bucketName, String objectName,
                                  String srcBucketName, String srcObjectName){
        System.out.println("----------文件对象拷贝----------");
        if (statObject(bucketName, objectName)){
            try {
                CopyObjectArgs args = CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(bucketName).object(objectName).build())
                        .bucket(srcBucketName)
                        .object(srcObjectName)
                        .build();
                minioClient.copyObject(args);
                System.out.println("存储桶["+bucketName+"]中文件["+objectName+"]，" +
                        "成功拷贝至存储桶["+srcBucketName+"]文件["+srcObjectName+"]。");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件对象删除
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     */
    public static void removeObject(String bucketName, String objectName){
        System.out.println("----------文件对象删除----------");
        if (statObject(bucketName, objectName)){
            try {
                RemoveObjectArgs args = RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build();
                minioClient.removeObject(args);
                System.out.println("存储桶["+bucketName+"]中文件["+objectName+"]删除成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
