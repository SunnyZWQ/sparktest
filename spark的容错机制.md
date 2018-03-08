# spark的容错机制
>Spark operates on data in fault-tolerant file systems like HDFS or S3. So all the RDDs generated from fault tolerant data is fault tolerant. But this does not set true for streaming/live data (data over the network). So the key need of fault tolerance in Spark is for this kind of data.\
[source](https://data-flair.training/blogs/fault-tolerance-in-apache-spark/)
### 分析
- 如果spark操作的数据是从一个 本身具有容错机制的文件系统中 获取，那么这些数据的RDD就是具有容错机制的
- 但是对于 从网络中获取的数据 以及从 spark streaming获取的数据 的容错机制需要额外讨论
### 总结
**spark的容错机制主要讨论的是：**
- 从网络中获取的数据
- 从spark streaming获取到的数据


- rdd容错的恢复：使用谱系图（lineage graph）
    - 每一次tansformation都产生一个新的RDD
    - RDD是自然状态下是保持不变的\
\
===>RDD容易恢复