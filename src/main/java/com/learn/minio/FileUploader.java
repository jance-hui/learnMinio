package com.learn.minio;

import com.learn.minio.constants.BaseConstants;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileUploader {
    public static void main(String[] args) {
        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient.Builder()
                    .endpoint(BaseConstants.MinioClient.IP, BaseConstants.MinioClient.PORT, false)
                    .credentials(BaseConstants.MinioClient.ACCESS_KEY, BaseConstants.MinioClient.SECRET_KEY)
                    .build();

            // 检查存储桶是否已经存在
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket("learn").build();
            boolean isExist = minioClient.bucketExists(bucketExistsArgs);
            if(isExist) {
                System.out.println("存储桶[learn]已存在。");
            } else {
                // 创建一个名为learn的存储桶。
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket("learn").build();
                minioClient.makeBucket(makeBucketArgs);
                System.out.println("存储桶[learn]创建完成。");
            }

            // 使用putObject上传一个文件到存储桶中。
            File file = new File("D:/aaa.txt");
            InputStream in = new FileInputStream(file);
            String fileName = file.getName();
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket("learn")
                    .object(fileName).stream(in, in.available(), -1).build();
            minioClient.putObject(putObjectArgs);
            System.out.println("文件[D:/aaa.txt]，成功上传到存储桶[learn]中。");
            in.close();
        } catch(Exception e) {
            System.out.println("异常: " + e);
        }
    }
}
