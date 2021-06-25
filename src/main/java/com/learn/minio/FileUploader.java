package com.learn.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {
    public static void main(String[] args) {
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient.Builder()
                    .endpoint("http://42.192.88.139", 9001, false)
                    .credentials("jancehui", "hui1221..")
                    .build();

            // 检查存储桶是否已经存在
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket("learn").build();
            boolean isExist = minioClient.bucketExists(bucketExistsArgs);
            if(isExist) {
                System.out.println("存储桶已存在。");
            } else {
                // 创建一个名为learn的存储桶。
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket("learn").build();

                minioClient.makeBucket(makeBucketArgs);
                System.out.println("存储桶[learn]创建完成。");
            }

            // 使用putObject上传一个文件到存储桶中。
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket("learn").build();
            minioClient.putObject(putObjectArgs);
            System.out.println("文件[D:/aaa.txt]，成功上传到存储桶[learn]中。");
        } catch(Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
