## streaming概述

![](http://spark.apache.org/docs/latest/img/streaming-arch.png)

从外部获取到的文件，经过spark streaming的处理，再存放到节点中的文件系统、数据库或者Dashboards中。


## streaming的处理过程
![](http://spark.apache.org/docs/latest/img/streaming-flow.png)

输入的data stream经过spark streaming之后，被处理成batches of input data，也就是生成了DStream

DStream是有序的RDDs的组合

之后再用spark engine对DStream的RDDs进行处理（详见spark RDD处理过程），生成的是处理过的DStream，这个新的DStream包含了新的处理过的RDDs

![](http://spark.apache.org/docs/latest/img/streaming-dstream.png)

![](http://spark.apache.org/docs/latest/img/streaming-dstream-ops.png)


## example code 分析

基于spark streaming 的 NetworkWordCount（伪代码，python）

[查看详细代码点击此处](https://github.com/apache/spark/blob/v2.3.0/examples/src/main/python/streaming/network_wordcount.py)

    from pyspark import SparkContext
    from pyspark.streaming import StreamingContext

    # Create a local StreamingContext with two working thread and batch interval of 1 second
    sc = SparkContext("local[2]", "NetworkWordCount")
    ssc = StreamingContext(sc, 1)
    
    # Create a DStream that will connect to hostname:port, like localhost:9999
    lines = ssc.socketTextStream("localhost", 9999)

    # Split each line into words
    words = lines.flatMap(lambda line: line.split(" ")) 

    # Count each word in each batch
    pairs = words.map(lambda word: (word, 1))
    wordCounts = pairs.reduceByKey(lambda x, y: x + y)

    # Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.pprint()

    ssc.start()             # Start the computation
    ssc.awaitTermination()  # Wait for the computation to terminate

使用 pyspark 执行 spark streaming example
    
    $ nc -lk 9999
    $ ./bin/spark-submit examples/src/main/python/streaming/network_wordcount.py localhost 9999


## 数据从后端到spark内存的过程

- 两个重要的定义：
    - **Input DStreams:** 输入的数据流-->RDDs-->DStreams
    - **Receivers:** 从sources中接受数据流，并将其存储在spark内存中，为了之后的计算

- 每一个Input DStream对应一个Receiver。

- Steaming sources
    - Basic sources: file systems, socket connections
    - Advanced sources: Kafka, Flume, Kinesis, etc


### 关于core的分配

情景：在streaming application中并行接受多个data streams时，可以创建多个input DStreams

条件：

1. 创建多个input DStreams --> 产生多个Receivers同时接收多个data streams

2. 一个spark executor是一个长时运行的任务 --> 一个executor占据一个core（分配给Spark Streaming application的cores中的一个）

**结论：** 

1. 一个input DStream需要一个core
2. 一个Receiver需要一个core
3. 在并行接收多个data stream时，需要创建多个input DStream，以及等数量的Reciever
4.  Spark Streaming application需要分配足够多的cores，不然没分配到core的receiver或者input DStream会不工作
















