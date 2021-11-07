### 05week作业说明

[必做1](https://github.com/lwakeepyoung/homework/tree/main/07week/07week/src/main/java/com/lwa/week/batchinsert)

使用自建数据源和Hikari数据源进行100万数据插入大概16秒左右
配置参数rewriteBatchedStatements=true，10秒内完成

Windows电脑


[必做2](https://github.com/lwakeepyoung/homework/tree/main/07week/07week/src/main/java/com/lwa/week/datasourceconfig)

手动数据源配置
- 创建DynamicDataSource继承AbstractRoutingDataSource
- 创建多数据源
- 编写注解和切面
- 通过注解的方式指定数据源进行操作


[必做3](https://github.com/lwakeepyoung/homework/tree/main/07week/shardingdemo)

使用
spring版本是2.1.5  
sharding-jdbc-spring-boot-starter版本是3.1.0  
使用新版本的spring boot(2.5.5) sharding-jdbc-spring-boot-starter(4.1.1)失效。
1. 请问下，在哪能找到相关文档，查看具体对应版本?
2. 官网上的shardingsphere-jdbc-core文档中只有两个配置类，是否有具体的文档和demo?


