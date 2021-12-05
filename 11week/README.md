### 11week作业说明

#### 必做1
##### 在 Java 中实现一个简单的分布式锁
- jedis实现 [链接](src/main/java/redis/lock/JedisLockImpl.java)
- redisson实现 [链接](src/main/java/redis/lock/RedissonLockImpl.java)
- RedisTemplate实现 [链接](src/main/java/redis/lock/RedisLockImpl.java)
[测试类](src/test/java/LockTest.java)
#### 在 Java 中实现一个分布式计数器，模拟减库存。
[链接](src/main/java/redis/counter/Counter.java) 
[测试类](src/test/java/CounterTest.java)
#### 必做2
##### 基于 Redis 的 PubSub 实现订单异步处理
[pubsub](src/main/java/redis/pubsub)
[测试类](src/test/java/PubSubTest.java)


