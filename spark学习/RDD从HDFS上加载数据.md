# RDD从HDFS上加载数据
### 概述
HDFS是一个分布式文件系统，每个节点都分别存储自己的信息以及备份信息（容错），spark在处理过程中如果想从HDFS上读数据，那么每个node只读取本节点中的hdfs存储的数据

### hdfs
hdfs是按照block存储，每个block默认是128MB

#### examples
如果有30GB的数据，HDFS的block大小是128MB，那么spark将数据分为30x1024/128=240个 或者30x1000/128≈235个 partition


# sc.parallelize()发生了什么
driver node将RDD分成partition。HDFS上的一个block对应一个partition。\
集群上的每一个节点，只将本节点的硬盘上存储在hdfs上的数据加载到内存中进行计算

>an RDD has enough information about how it was
derived from other datasets (its lineage) to compute its partitions from data in stable storage. 

分析：RDD中含有足够的信息，包括它是怎么从其他的datasets中衍变过来（根据谱系图），根据这样的信息能计算它的在稳定存储系统中的partitions




