## streaming笔记输出

- [ ] receiver和processor的具体的交互过程
- [ ] block, interval, batch
- [ ] streaming如何实现实时交互
- [ ] update streaming application时需要注意什么
- [ ] kafka, flume之类的组件起到了什么作用


----

## Receiver

一个Input DStream对应一个Receiver

一个streaming application可以创建多个Input DStream，用来并行接收多个数据流

一个core处理一个receiver或者一个executor。所以通常，给streaming application分配的cores数量 > Receivers（不然都用来接收数据，没有core用来处理数据）

注意：filesystem不需要Receiver，因此不需要给Receiver分配core

### receiver和processor的具体的交互过程

Receiver接收数据之后将数据存放到spark内存中，等待processor处理。

### block, interval, batch--->详解DStream

spark --------abstraction----> RDD

streaming ----abstraction----> DStream

一个DStream中的一个RDD代表一个interval内接收到的数据

对DStream的操作实际上是对DStream中的RDDs的操作。


## 实时监控

- filesystem

输入的参数：需要监控的目录地址\
直接存放在这个目录下的文件只要被发现，就会被处理（所有文件的格式必须相同）\
决定一个文件是否是这个时间周期的因素是：modification time，而不是创建时间

output stream 与 modification time：

- 对于完整的文件系统（如HDFS）：当打开一个文件时，甚至数据还没有被完全写入，这个文件就被包含在DStream里了。这个时间就是文件的modification time。
- 在这之后，在同一个window下的对文件的更新都会被忽视
- 这意味着：更改可能被忽视，数据可能从stream中丢失
- 为了保证在一个窗口内能够完成全部的更新，将文件写入到一个未被监视的地址中，在output stream关闭之后，立刻将文件重命名到目标地址。

"忽视更新"：


























