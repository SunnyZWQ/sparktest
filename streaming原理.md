## Receiver

一个Input DStream对应一个Receiver

一个streaming application可以创建多个Input DStream，用来并行接收多个数据流

一个core处理一个receiver或者一个executor。所以通常，给streaming application分配的cores数量 > Receivers（不然都用来接收数据，没有core用来处理数据）

注意：filesystem不需要Receiver，因此不需要给Receiver分配core



## 实时监控

- filesystem

输入的参数：需要监控的目录地址\
直接存放在这个目录下的文件只要被发现，就会被处理（所有文件的格式必须相同）\
决定一个文件是否是这个时间周期的因素是：更改时间，而不是创建时间

"忽视更新"：


























