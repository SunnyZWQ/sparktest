## spark出现的原因：
- 迭代式的算法
- 迭代式的数据挖掘工具
    - 同一个数据集上的adhoc queries

## RDD支持
- 运算的中间结果持久化在内存里
- 通过控制分区来优化数据存放
- 用丰富的operator来处理数据

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




