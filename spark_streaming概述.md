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
    - Basic sources: file systems, socket connections(可以直接从 StreamingContext API 中获取的)
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
4.  Spark Streaming application需要分配足够多的cores，不然没分配到core的receiver或者executor会不工作
5.  通常，cores的数量 > receivers数量

tips：
1. 当以local模式运行时，指定master URL时，使用“local[n]”【n > receivers】
2. 分布式同理，分配的cores数量 > receivers，否则只能接收数据，不能处理数据
3. **FileStream不需要Receiver，因此不需要分配cores给Receiver**
4. Python不能用fileStream，但是可以用textFileStream


## Spark Streaming 如何实现实时的数据传输

streaming会监视被指定的数据源的地址，一旦有新的文件产生，streaming就会对新产生的文件进行处理

**tips：**
1. "hdfs://namenode:8040/logs/"
2. "hdfs://namenode:8040/logs/2017/*"
3. 判断一个文件是不是一个时间周期的一部分，要根据它的修改时间，而不是创建时间。

#### 如何避免文件被重复处理——忽视更新
“忽视更新”：文件被streaming处理后，造成的文件更改，在当前窗口不会重新读取文件，这样避免了文件的重复处理

#### 如何避免文件在窗口内，还没来得及更改，窗口就已经被关闭

- “Full Filesystems” like HDFS
    - 一旦 output stream 被创建，就在需要被更改的文件上设立modification time。在文件打开时，即使数据还没被完全写入，就已经创立在这个文件上的RDD-->DStream
    - 在此之后，对同一窗口内的文件更新将被忽略。也就是说，可能会丢失更改，或者从流中丢弃数据。

    - 结论：为了保证这些更改在一个窗口内进行，将文件写入一个不被streaming监视的地址里，然后在output stream结束时，立刻将这个文件地址改成目标地址（被监视的）。在这个窗口的创立期间，保证文件出现在监视地址内，那么就能监视到新的数据。

- Object Stores such as Amazon S3 and Azure Storage
    - 这种文件系统在更改文件路径时会运行的很慢，因为数据是被copy进去的。而且，对文件的更改，不仅包括文件更改时间，还包括rename time。因此，可能不会包含在这个文件对应的window的一部分。
    - 结论：对于object store filesystem，正确的策略是直接将数据写入目标文件（被监视的）

## 使用测试数据对Spark Streaming Application进行测试

create a DStream based on a queue of RDDs

    streamingContext.queueStream(queueOfRDDs)

每个被push到这个队列RDD会被当做 DStream 的 a batch of data，并且像一个stream一样被处理，[点此查看详细python API](http://spark.apache.org/docs/latest/api/python/pyspark.streaming.html#pyspark.streaming.StreamingContext)


## 接收器的可靠性

- 可靠的接收器：数据成功接收会对source发送确认回复
- 不可靠的接收器：不发送确认回复。应用于不支持确认消息的sources，或者是不想接收复杂的确认消息的支持确认机制的sources

## UpdateStateByKey Operation

作用：在用新数据持续更新时，能够保存其中的任意一个state

步骤：
1. 定义 state —— The state can be an arbitrary data type.
2. 定义 state update function —— Specify with a function how to update the state using the previous state and the new values from an input stream.


#### example——在text data stream中记录运行中的每个单词的数量
[点击此处查看完整代码](https://github.com/apache/spark/blob/v2.3.0/examples/src/main/python/streaming/stateful_network_wordcount.py)

    def updateFunction(newValues, runningCount):
    if runningCount is None:
        runningCount = 0
    return sum(newValues, runningCount)  # add the new values with the previous running count to get the new count

    runningCounts = pairs.updateStateByKey(updateFunction)

## 关于 transform operation
> **transform(func)** \
Return a new DStream in which each RDD is generated by applying a function on each RDD of this DStream.\
func can have one argument of rdd, or have two arguments of (time, rdd)

在DStream的API中，可能没有想要的transformation，这时可以调用transform方法，参数设置为可以对RDD做的操作。极大地提高了streaming的灵活性

    spamInfoRDD = sc.pickleFile(...)  # RDD containing spam information

    # join data stream with spam information to do data cleaning
    cleanedDStream = wordCounts.transform(lambda rdd: rdd.join(spamInfoRDD).filter(...))



## DStream的lazy特性
就像RDD直到action时才会执行之前所有的操作，DStream直到有output operations时才会执行之前所有的操作。

带有RDD actions的DStream output operation才能强迫DStream对接收的数据进行处理。

因此，如果你的application中没有output operations 或者是 你的output operation中不含有对RDD的actions(如：dstream.foreachRDD()），那么不会执行任何操作。系统只会接收数据，然后抛弃数据。


## 使用 foreachRDD 的设计模式
    def sendPartition(iter):
        # ConnectionPool is a static, lazily initialized pool of connections
        connection = ConnectionPool.getConnection()
        for record in iter:
            connection.send(record)
        # return to the pool for future reuse
        ConnectionPool.returnConnection(connection)

    dstream.foreachRDD(lambda rdd: rdd.foreachPartition(sendPartition))

## 关于Checkpointing

分类：
- Metadata checkpointing——恢复driver node（运行driver program的node）
- Data checkpointing——将生成的RDD保存到可靠的存储系统中

### 什么时候需要用Checkpointing

- 使用stateful transformations时
    - updateStateByKey
    - reduceByKeyAndWindow (with inverse function) 
- 使用Metadata checkpointing恢复application的driver
- 注：一些简单的applications可能不需要stateful transformations，或者metadata checkpointing

#### checkpointing的配置

在一个可靠的、具有容错机制的文件系统中设置目录，即可使用checkpointing。信息将会被存储到这个目录中。

- 对于data checkpointing

    streamingContext.checkpoint(checkpointDirectory)

- 对于metadata checkpointing，需要重写你的application，使它具有以下行为：
    - 程序第一次启动时，它会创造一个新的StreamingContext，建立所有的streams，然后调用start()方法。
    - 程序运行失败后重启时，它会从断点目录的断点数据重建一个StreamingContext







