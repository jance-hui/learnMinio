# learnMinio
Minio分布式文件系统学习-java

MinIO 是一个基于Apache License v2.0开源协议的对象存储服务。它兼容亚马逊S3云存储服务接口，非常适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份数据和容器/虚拟机镜像等，而一个对象文件可以是任意大小，从几kb到最大5T不等。

MinIO是一个非常轻量的服务,可以很简单的和其他应用的结合，类似 NodeJS, Redis 或者 MySQL。

## 服务器搭建
> 前提

服务器安装 docker

执行以下命令

```bash
# -d 后台运行
# -p 9001:9000 端口映射：主机端口9001映射容器9000
# --name minio 容器命名为 minio
# -e "MINIO_ROOT_USER=jancehui"   设置环境变量：用户名
# -e "MINIO_ROOT_PASSWORD=hui1221.."   设置环境变量：密码
# -v /home/minio/data:/data  挂载数据到主机/home/minio/data
# -v /home/minio/config:/root/.minio  挂载配置到/home/minio/config

docker run -d -p 9001:9000 --name minio \
  -e "MINIO_ROOT_USER=jancehui" \
  -e "MINIO_ROOT_PASSWORD=hui1221.." \
  -v /home/minio/data:/data \
  -v /home/minio/config:/root/.minio \
  minio/minio server /data
```

## 接口API
### 存储桶操作
- `bucketExists`：检查存储通是否存在
- `makeBucket`：创建存储桶
- `listBuckets`：列出所有存储桶
- `removeBucket`：删除存储桶
- `listObjects`：列出某个存储桶中的所有对象
- `getBucketPolicy`：获得指定存储桶策略
- `setBucketPolicy`：给指定存储桶设置策略

参考文件：`MinioBucketTest.java`
### 文件对象操作
- `statObject`：检查文件对象是否存在
- `getObject`：下载文件对象
- `putObject`：上传文件对象/创建文件夹
- `copyObject`：文件对象拷贝
- `removeObject`：文件对象删除

参考文件：`MinioFileTest.java`
### 外链操作
- `getPresignedObjectUrl`：获取文件外链

参考文件：`MinioPresignedTest.java`
