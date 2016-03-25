![](http://dl.iteye.com/upload/picture/pic/135281/e0f25517-ae0c-3af9-a910-b8c05b4436ff.jpg)
## Shark简介 [![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) [![Join the chat at https://gitter.im/gaoxianglong/shark](https://badges.gitter.im/gaoxianglong/shark.svg)](https://gitter.im/gaoxianglong/shark?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![GitHub release](https://img.shields.io/github/release/gaoxianglong/shark.svg)](https://github.com/gaoxianglong/shark/releases) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.sharksharding/shark/badge.svg)](http://search.maven.org/#artifactdetails%7Ccom.sharksharding%7Cshark%7C1.3.8%7Cjar/)

轻量级分布式mysql分库分表(sharding)中间件，丰富的sharding算法支持(2类4种分片算法)，能够方便DBA实现库的水平扩容和降低数据迁移成本。shark站在巨人的肩膀上(springjdbc、druid)，采用与应用集成架构，放弃通用性，只为换取更好的执行性能与降低分布式环境下外围系统的宕机风险。<br>

- [使用指南](https://github.com/gaoxianglong/shark/wiki/%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97)<br>
- [常见问题](https://github.com/gaoxianglong/shark/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)<br>
- [基准测试](https://github.com/gaoxianglong/shark/wiki/shark-benchmark-result)<br>

----------

## Shark的优点
- 具备丰富、完善的技术文档支持；<br>
- 动态数据源的无缝切换；<br>
- master/slave一主一从读写分离；<br>
- sql独立配置，与逻辑代码解耦；<br>
- 单线程读重试(取决于的数据库连接池是否支持)；<br>
- 友好支持mysql数据库；<br>
- 非proxy架构，与应用集成，应用直连数据库，降低外围系统依赖所带来的宕机风险；<br>
- 使用简单，侵入性低，站在巨人的肩膀上，依赖于springjdbc、druid；<br>
- 基于淘宝druid的sqlparser完成sql解析任务，解析性能高效、稳定；<br>
- 分库分表路由算法支持2类4种分片模式，库内分片/一库一片；<br>
- 提供自动生成全局唯一的sequenceid的API支持；<br>
- 支持基于zookeeper、redis3.x cluster作为集中式资源配置中心；<br>
- 提供自动生成配置文件的支持，降低配置出错率；<br>
- 提供内置验证页面，方便开发、测试以及运维对执行后的sql进行验证；<br>
- 专注于sharding领域，无需兼容通用性，因此核心代码量少、易读易维护；<br>

----------

## Shark的分片模型
##### Shark支持2类4种分片算法：
- 库内分片类型：
  - 片名连续的库内分片算法；
  - 非片名连续的库内分片算法；
- 一库一片类型：
  - 片名连续的一库一片算法；
  - 非片名连续的一库一片算法；

----------

## Shark的使用注意事项
- 不支持强一致性的分布式事务，建议在业务层采用依赖MQ、异步操作等方式实现事物，保证事物的最终一致性；
- 不建议、不支持多表查询，所有多表查询sql，务必全部打散为单条sql逐条执行；
- sql语句的第一个参数务必是路由条件；
- 不支持sql语句中出现数据库别名；
- 路由条件必须是整数类型；

----------

## 学习 & 联系我们
- 项目主页：https://github.com/gaoxianglong/shark
- wiki：https://github.com/gaoxianglong/shark/wiki
- issues：https://github.com/gaoxianglong/shark/issues
- ![](http://dl.iteye.com/upload/picture/pic/134683/97e5d3af-cb7b-3115-97c1-230cbf6ad081.png): QQ Group:150445731
- blog：http://gao-xianglong.iteye.com
- email：gao_xianglong@sharksharding.com

----------

## 典型案例
![](http://dl.iteye.com/upload/picture/pic/135357/01760d0f-d0ff-3606-ac9c-1d99f94f0e30.jpg)　 ![](http://dl.iteye.com/upload/picture/pic/135363/44675c67-00b5-3783-9ce8-c1ddb67f645e.png)

----------
