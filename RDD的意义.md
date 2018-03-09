### 官网对RDD的定义
>Spark revolves around the concept of a resilient distributed dataset (RDD), which is a fault-tolerant collection of elements that can be operated on in parallel.

#### 分析：

- RDD是对数据集的操作
- RDD具有[容错机制](https://data-flair.training/blogs/fault-tolerance-in-apache-spark/)
- RDD可以并行化处理

#### RDD features：
- lazy
    - 意义：只有需要用的时候才会被调用，这样节省了时间，提升了效率
    - 原理：
        1. pipeline所有的tansformation
        2. 执行action时才对数据进行处理（transformation-->action）
    - tips：transformation分为 narrow ~ 和 wide ~ ，只有transformation是narrow时才可以叠加多个transformation到pipeline中，或者 + narrow | wide | action

### narrow transformation 与 wide transformation
![](http://ww1.sinaimg.cn/large/005N2p5vgy1fp5mvvadojj30ia09lq58.jpg)
![](http://ww1.sinaimg.cn/large/005N2p5vgy1fp5mwby844j30ib09lac7.jpg)


### why RDD in spark ?
- 使用迭代算法
- 交互式的data mining tools
- 弥补了DSM（Distributed Shared Memory）在容错机制上的不足[（点此查看spark容错机制）](https://github.com/SunnyZWQ/sparktest/blob/master/spark%E7%9A%84%E5%AE%B9%E9%94%99%E6%9C%BA%E5%88%B6.md)
- 存储在HDFS等的文件系统上的数据存储在硬盘上，计算数据时会有I/O、复制、序列化的开销
    - ***TODO***：RDD在sc.parallelize()时做了什么，使得从硬盘读取的数据加载到内存中变快

### RDD的处理机制分析
#### Straggler Mitigation
###### RDD可以缓解拖延，通过 backup tasks
#### 内存不够时
###### RDDs are shifted to the disks

>The RDD describes the partitions that, when an action is called, become tasks that will read their parts of the input file.


---
### 一个RDD指向所有的数据，所有的数据分布在各个节点上，


## resources stackoverflow
>An RDD is, essentially, the Spark representation of a set of data, spread across multiple machines, with APIs to let you act on it. An RDD could come from any datasource, e.g. text files, a database via JDBC, etc.\
The formal definition is:\
RDDs are fault-tolerant, parallel data structures that let users explicitly persist intermediate results in memory, control their partitioning to optimize data placement, and manipulate them using a rich set of operators.\
If you want the full details on what an RDD is, read one of the core Spark academic papers, Resilient Distributed Datasets: A Fault-Tolerant Abstraction for In-Memory Cluster Computing（RDD论文）\
[source](https://stackoverflow.com/questions/34433027/what-is-rdd-in-spark?rq=1)



>When Spark reads a file from HDFS, it creates a single partition for a single input split. Input split is set by the Hadoop InputFormat used to read this file. For instance, if you use textFile() it would be TextInputFormat in Hadoop, which would return you a single partition for a single block of HDFS (but the split between partitions would be done on line split, not the exact block split), unless you have a compressed text file. In case of compressed file you would get a single partition for a single file (as compressed text files are not splittable).\
When you call rdd.repartition(x) it would perform a shuffle of the data from N partititons you have in rdd to x partitions you want to have, partitioning would be done on round robin basis.\
If you have a 30GB uncompressed text file stored on HDFS, then with the default HDFS block size setting (128MB) it would be stored in 235 blocks, which means that the RDD you read from this file would have 235 partitions. When you call repartition(1000) your RDD would be marked as to be repartitioned, but in fact it would be shuffled to 1000 partitions only when you will execute an action on top of this RDD (lazy execution concept)\
[source](https://stackoverflow.com/questions/29011574/how-does-spark-partitioning-work-on-files-in-hdfs)
