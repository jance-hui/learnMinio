package com.learn.minio.bucker;

import io.minio.*;
import io.minio.messages.Bucket;

import java.util.List;

/**
 * Minio 存储桶测试：
 * 创建、查询、删除存储桶
 * 查看存储桶文件
 */
public class MinioBucketTest {
    private static MinioClient minioClient;

    static {
        minioClient = new MinioClient.Builder()
                .endpoint("http://42.192.88.139", 9001, false)
                .credentials("minioadmin", "minioadmin")
                .build();
    }

    public static void main(String[] args) {
        createBucket("test2");
        listBucket();
        removeBucket("test2");
        listBucket();
//        listObjects("test",null,true,false);
    }

    /**
     * 检查存储桶是否存在
     * @param bucketName 存储桶名称
     */
    public static boolean isBucketExists(String bucketName){
        System.out.println("----------检查存储桶是否存在----------");
        boolean found = false;
        try {
            BucketExistsArgs args = BucketExistsArgs.builder().bucket(bucketName).build();
            found = minioClient.bucketExists(args);
            if (found){
                System.out.println("存储桶["+bucketName+"]已存在");
            } else {
                System.out.println("存储桶["+bucketName+"]不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }

    /**
     * 创建存储桶
     * @param bucketName 存储桶名称
     */
    public static void createBucket(String bucketName){
        System.out.println("----------创建存储桶----------");
        if (!isBucketExists(bucketName)){
            try {
                MakeBucketArgs args = MakeBucketArgs.builder().bucket(bucketName).build();
                minioClient.makeBucket(args);
                System.out.println("存储桶["+bucketName+"]创建完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 列出所有存储桶
     */
    public static void listBucket(){
        System.out.println("----------列出所有存储桶----------");
        try {
            List<Bucket> list = minioClient.listBuckets();
            if (list.isEmpty()){
                System.out.println("暂无存储桶信息");
            } else {
                System.out.println("存在一下存储桶信息：");
                for (Bucket bucket : list) {
                    System.out.println("存储桶名称:"+bucket.name()+",创建时间:"+bucket.creationDate());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除存储桶
     * @param bucketName 存储桶名称
     */
    public static void removeBucket(String bucketName){
        System.out.println("----------删除存储桶----------");
        if (isBucketExists(bucketName)){
            try {
                RemoveBucketArgs args = RemoveBucketArgs.builder().bucket(bucketName).build();
                minioClient.removeBucket(args);
                System.out.println("存储桶["+bucketName+"]删除完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//
//    /**
//     * 列出某个存储桶中的所有对象
//     * @param bucketName 存储桶名称
//     * @param prefix 对象名称的前缀
//     * @param recursive 是否递归查找，如果是false,就模拟文件夹结构查找。
//     * @param useVersion1 如果是true, 使用版本1 REST API
//     */
//    public static void listObjects(String bucketName, String prefix, boolean recursive, boolean useVersion1){
//        System.out.println("----------列出某个存储桶中的所有对象----------");
//        if (isBucketExists(bucketName)){
//            try {
//                ListObjectsArgs args = ListObjectsArgs.builder().bucket(bucketName).build();
//                Iterable<Result<Item>> myObjects = minioClient.listObjects(bucketName, prefix, recursive, useVersion1);
//                if (myObjects.spliterator().getExactSizeIfKnown() == 0){
//                    System.out.println("存储桶["+bucketName+"]中没有文件/文件夹");
//                } else {
//                    System.out.println("存储桶["+bucketName+"]中文件/文件夹信息如下：");
////                    for (Result<Item> result : myObjects) {
////                        Item item = result.get();
////                        System.out.println("文件名:"+item.objectName()+",上次修改时间:"+item.lastModified()
////                                +",文件大小:"+item.objectSize()+"B,是否文件夹:"+item.isDir());
////                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    /**
//     * 列出存储桶中被部分上传的对象
//     * @param bucketName 存储桶名称
//     * @param prefix 对象名称的前缀
//     * @param recursive 是否递归查找，如果是false,就模拟文件夹结构查找。
//     */
//    public static void listIncompleteUploads(String bucketName, String prefix, boolean recursive){
//        System.out.println("----------列出存储桶中被部分上传的对象----------");
//        if (isBucketExists(bucketName)){
//            try {
//                Iterable<Result<Upload>> myObjects = minioClient.listIncompleteUploads(bucketName, prefix, recursive);
//                if (myObjects.spliterator().getExactSizeIfKnown() == 0){
//                    System.out.println("存储桶["+bucketName+"]中没有部分上传的文件");
//                } else {
//                    System.out.println("存储桶["+bucketName+"]中部分上传的文件如下：");
//                    for (Result<Upload> result : myObjects) {
//                        Upload item = result.get();
//                        System.out.println("文件名:"+item.objectName()+",正在上传");
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 获得指定存储桶策略
//     * @param bucketName 存储桶名称
//     */
//    public static void getBucketPolicy(String bucketName){
//        System.out.println("----------获得指定存储桶策略----------");
//        if (isBucketExists(bucketName)){
//            try {
//                System.out.println("存储桶["+bucketName+"]策略:"+minioClient.getBucketPolicy(bucketName));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 给指定存储桶设置策略
//     * @param bucketName 存储桶名称
//     * @param policy 要赋予的策略
//     */
//    public static void setBucketPolicy(String bucketName, String policy){
//        System.out.println("----------给指定存储桶设置策略----------");
//        if (isBucketExists(bucketName)){
//            try {
//                minioClient.setBucketPolicy(bucketName, policy);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
