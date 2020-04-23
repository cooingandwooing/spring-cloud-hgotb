# 1 简介

- 重写[spring-cloud-online-exam](https://gitee.com/wells2333/spring-cloud-online-exam)

- 采用前后端分离技术

- 前端采用`vue2.0`，通过`jwt`和后端交互

- 后端基于`spring boot`、`spring cloud`、`MySQL`等技术实现权限管理、信息管理等功能。

# 2 在线体验
- 暂无


# 3 技术选型

- 服务注册与发现：`Consul`
- 熔断器：`Hystrix` + `Turbine`
- 客户端负载均衡：`Ribbon`
- 内部服务调用：`Feign`
- 网关：`Spring Cloud Gateway`
- 认证鉴权：`Spring Cloud OAuth2` + `JWT`
- 程序监控：`Spring Boot Admin` / `Spring Boot Actuator`
- 分布式配置中心：`Spring Cloud Config`
- 分布式调用链监控：`Spring Cloud Sleuth` + `Zipkin`
- 数据库：`MySQL 5.7`
- 部署：`Docker` + `k8s`
- 构建工具：`Maven`
- 后台 API 文档：`Swagger`
- 消息队列：`RabbitMQ`
- 文件系统：`FastDFS`
- 缓存：`Redis` @see RedisConfig and MultitenantCacheManager CustomRedisCacheWriter 配置中没有开启集群
- 前端：`vue`

# 4 核心依赖

|      名称      |   版本    |
| --------- | -------- |
| `Spring Boot`    | `2.1.8.RELEASE`  |
| `Spring Cloud`   | `Greenwich.SR3`  |

# 5 系统架构

![image](docs/images/系统架构图v3.0.jpg)

# 6 功能概述

项目分前台网站和后台管理两部分，前台主要提供展示功能，后台提供基础管理、通用信息系统后台功能。

前台主要提供信息系统展示功能

后台管理分为：系统管理、系统监控、附件管理、个人管理

预计包含自动生成前后端代码功能。

系统管理：提供用户、部门、角色、权限等基础管理
- 用户管理：用户信息增删改查、导入导出
- 部门管理：部门信息增删改查
- 角色管理：角色信息增删改查、分配权限
- 菜单管理：菜单信息增删改查、导入导出
- 终端管理：管理OAuth2.0的客户端，如client_id、client_secret、access_token有效时间等
- 路由管理：网关路由管理，包括路由的增删改查、刷新路由等

系统监控：监控服务、日志等
- 日志监控：查看系统日志
- `consul`监控：`consul`服务监控
- `zipkin`监控：监控服务的调用链路
- 服务监控：`spring boot admin`服务监控
- 接口文档：`swagger api`文档


附件管理：项目的所有附件存储在`fastDfs`里，提供统一的管理入口
- 附件列表：管理所有附件，如用户头像、文件附件等。

个人管理：管理个人资料和修改密码
- 个人资料：姓名、头像等基本信息的修改
- 修改密码：修改密码
