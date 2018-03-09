# RDD从HDFS上加载数据
### 概述
HDFS是一个分布式文件系统，每个节点都分别存储自己的信息以及备份信息（容错），spark在处理过程中如果想从HDFS上读数据，那么每个node只读取本节点中的hdfs存储的数据

### hdfs
hdfs是按照block存储，每个block默认是128MB

#### examples
如果有30GB的数据，HDFS的block大小是128MB，那么spark将数据分为30x1024/128=240个 或者30x1000/128≈235个 partition



