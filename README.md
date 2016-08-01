![](http://dl.iteye.com/upload/picture/pic/135981/3e89a292-3c91-374d-93c3-5b5836bf0926.png)
## Shark简介 [![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) [![Join the chat at https://gitter.im/gaoxianglong/shark](https://badges.gitter.im/gaoxianglong/shark.svg)](https://gitter.im/gaoxianglong/shark?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![GitHub release](https://img.shields.io/github/release/gaoxianglong/shark.svg)](https://github.com/gaoxianglong/shark/releases) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.sharksharding/shark/badge.svg)](http://search.maven.org/#artifactdetails%7Ccom.sharksharding%7Cshark%7C1.3.8%7Cjar/)
分布式mysql分库分表中间件，sharding领域的一站式解决方案。具备丰富、灵活的路由算法支持，能够方便DBA实现库的水平扩容和降低数据迁移成本。shark采用应用集成架构，放弃通用性，只为换取更好的执行性能与降低分布式环境下外围系统的宕机风险。**目前shark每天为不同的企业、业务提供超过千万级别的sql读/写服务**。<br>

- [用户指南](http://gaoxianglong.github.io/shark/)<br>
- [基准测试](https://github.com/gaoxianglong/shark/wiki/shark-benchmark-result)<br>

----------

## Shark的优点
- 完善的技术文档支持；<br>
- 动态数据源的无缝切换；<br>
- 丰富、灵活的分布式路由算法支持；<br>
- 非proxy架构，应用直连数据库，降低外围系统依赖所带来的宕机风险；<br>
- 业务零侵入，配置简单；<br>
- 站在巨人的肩膀上(springjdbc、基于druid的sqlparser完成sql解析任务)，执行性能高效、稳定；<br>
- 提供多机sequenceid的API支持，解决多机sequenceid难题；<br>
- 支持基于zookeeper、redis3.x cluster作为集中式资源配置中心；<br>
- 基于velocity模板引擎渲染内容，支持sql语句独立配置和动态拼接，与业务逻辑代码解耦；<br>
- 提供内置验证页面，方便开发、测试及运维人员对执行后的sql进行验证；<br>
- 提供自动生成配置文件的API支持，降低配置出错率；<br>

----------

## Shark总体架构
Shark采用应用集成架构，其领域模型位于持久层(JdbcTemplate)和JDBC之间，也就是分布式数据路由层。<br>
![](http://dl.iteye.com/upload/picture/pic/135419/0cd4a534-3a06-36d7-9aef-9ce469d3e8c7.jpg)

----------

## Shark与其它Sharding中间件功能对比
我们并不认为Shark是最优秀的，但却始终坚信Shark是最好用的。

| 功能          | Cobar         | Mycat         | Heisenberg     | Shark          | TDDL          | Sharding-JDBC |
| ------------- | ------------- | ------------- | -------------- | -------------- | ------------- | ------------- |
| 是否开源      | 开源          | 开源          | 开源           | 开源           | 部分开源      | 开源          |
| 架构模型      | Proxy架构     | Proxy架构     | Proxy架构      | 应用集成架构   | 应用集成架构  | 应用集成架构  |
| 数据库支持    | MySQL         | 任意          | 任意           | MySQL          | 任意          | MySQL         |
| 外围依赖      | 无            | 无            | 无             | 无             | Diamond       | 无            |
| 使用复杂度    | 一般          | 一般          | 一般           | 简单           | 复杂          | 一般          |
| 技术文档支持  | 较少          | 付费          | 较少           | 丰富           | 无            | 一般          |

----------

## Shark的使用注意事项
- 不支持强一致性的分布式事务，建议在业务层依赖MQ，保证最终数据一致性；
- 不建议、不支持多表查询，所有多表查询sql，务必全部打散为单条sql逐条执行；
- sql语句的第一个参数务必是shard key；
- shard key必须是整数类型；

----------

## 学习 & 联系我们
- wiki：https://github.com/gaoxianglong/shark/wiki
- issues：https://github.com/gaoxianglong/shark/issues
- blog：http://gao-xianglong.iteye.com
- email：gaoxl@yunjiweidian.com

----------

## 典型案例
![](http://dl.iteye.com/upload/picture/pic/135357/01760d0f-d0ff-3606-ac9c-1d99f94f0e30.jpg)

----------
