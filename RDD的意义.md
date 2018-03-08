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
