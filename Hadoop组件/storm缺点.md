https://www.oschina.net/translate/beyond-storm-for-streaming-data-applications

https://blog.csdn.net/cainiaoxiaozhou/article/details/77261843

https://blog.csdn.net/yangbutao/article/details/44538637

http://www.aboutyun.com/thread-9912-1-1.html

http://calvin1978.blogcn.com/articles/stormnotes.html

### storm 的流处理方案不仅需要storm

Storm通常使用Kafka用于数据采集，而Storm和Kafka都需要Zookeeper用于管理状态。最后，还需要第四个系统用于管理状态。最终，当所有工作完成后，一个3结点（指计算节点）的Storm集群会很轻易地消耗掉12个结点！



### storm 需要写复杂的数据处理算法

乏味：大部份开发时间花费在部署worker，部署中间队列，配置消息发送到哪里。你所关心的实时处理逻辑只占了你的代码很少的比例 。

脆弱：你要自己负责保持每个worker和队列正常工作。

伸缩时痛苦：当worker或队列的消息吞吐量太高时，你需要重新分区，重新配置其它worker，让它们发送消息到新位置。

#### spark streaming 如何解决这个问题

DataFrame，SQL引擎的优化

### storm 通常用于简单的分析任务

诸如计算，以及清洗，使其常规化，并且准备摄入用于长期存储的数据。

Storm 不能够有状态操作，这对于进行实时决策非常重要。状态数据的分析正处于快速数据应用程序的中心，诸如：个性化，客户参与，推荐引擎，报警，授权与政策执行。无需诸如 Zookeeper与 Cassandra 之类的额外组件，Storm 不能查找维度数据，更新汇总，或者直接作用于一个事件（那就是，对实时进行决策）。

TODO：
- 推荐系统的状态是指什么（矩阵变换）？
- storm有checkpoint机制，为什么不能进行有状态操作？


### storm 计算与存储分离可能会削弱性能

如果storm有存储，















