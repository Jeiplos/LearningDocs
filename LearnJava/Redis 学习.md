# Redis 学习



## Redis 简介



REmote DIctionary Server(Redis) 是一个 **key-value** 存储系统，是跨平台的**非关系型**数据库。（ MySQL 是关系型数据库）

Redis 与其他 key - value 缓存产品有以下三个特点：

- Redis支持数据的**持久化**，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
- Redis不仅仅支持简单的key-value类型的数据，同时**还提供**list，set，zset，hash等数据结构的存储。
- Redis支持数据的**备份**，即master-slave模式的数据备份。
- 原子：Redis的所有操作都是原子性的，即要么成功执行要么失败完全不执行。单个操作是原子性的。多个操作也支持事务，即原子性，通过MULTI和EXEC指令包起来。



