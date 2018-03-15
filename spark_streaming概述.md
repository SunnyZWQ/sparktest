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






