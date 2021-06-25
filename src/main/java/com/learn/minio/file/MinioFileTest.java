//package com.learn.minio.file;
//
//import io.minio.MinioClient;
//import io.minio.Result;
//import io.minio.errors.InvalidEndpointException;
//import io.minio.errors.InvalidPortException;
//import io.minio.messages.Bucket;
//import io.minio.messages.Item;
//
//import java.util.List;
//
///**
// * Minio 文件测试：
// * 创建、查询、删除文件夹
// * 创建、查询、删除文件
// */
//public class MinioFileTest {
//    private static MinioClient minioClient;
//    private static String bucketName = "test";
//
//    static {
//        try {
//            minioClient = new MinioClient("http://42.192.88.139", 9001, "minioadmin", "minioadmin");
//        } catch (InvalidEndpointException | InvalidPortException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        createBucket("test2");
//        listBucket();
//        removeBucket("test2");
//        listBucket();
//        listObjects("test",null,true,false);
//    }
//
//    /**
//     * 创建存储桶
//     * @param bucketName 存储桶名称
//     */
//    public static void createBucket(String bucketName){
//        System.out.println("----------创建存储桶----------");
//        if (!isBucketExists(bucketName)){
//            try {
//                minioClient.makeBucket(bucketName);
//                System.out.println("存储桶["+bucketName+"]创建完成");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 检查存储桶是否存在
//     * @param bucketName 存储桶名称
//     */
//    public static boolean isBucketExists(String bucketName){
//        System.out.println("----------检查存储桶是否存在----------");
//        boolean found = false;
//        try {
//            found = minioClient.bucketExists(bucketName);
//            if (found){
//                System.out.println("存储桶["+bucketName+"]已存在");
//            } else {
//                System.out.println("存储桶["+bucketName+"]不存在");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return found;
//    }
//
//    /**
//     * 列出所有存储桶
//     */
//    public static void listBucket(){
//        System.out.println("----------列出所有存储桶----------");
//        try {
//            List<Bucket> list = minioClient.listBuckets();
//            if (list.isEmpty()){
//                System.out.println("暂无存储桶信息");
//            } else {
//                System.out.println("存在一下存储桶信息：");
//                for (Bucket bucket : list) {
//                    System.out.println("存储桶名称:"+bucket.name()+",创建时间:"+bucket.creationDate());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 删除存储桶
//     * @param bucketName 存储桶名称
//     */
//    public static void removeBucket(String bucketName){
//        System.out.println("----------删除存储桶----------");
//        if (isBucketExists(bucketName)){
//            try {
//                minioClient.removeBucket(bucketName);
//                System.out.println("存储桶["+bucketName+"]删除完成");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
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
//                Iterable<Result<Item>> myObjects = minioClient.listObjects(bucketName, prefix, recursive, useVersion1);
//                for (Result<Item> result : myObjects) {
//                    Item item = result.get();
//                    System.out.println("文件名:"+item.objectName()+",上次修改时间:"+item.lastModified()
//                            +",文件大小:"+item.objectSize()+"B,是否文件夹:"+item.isDir());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
