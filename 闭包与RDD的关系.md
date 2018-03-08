## 闭包出现的意义：（附带Accumulator）
在cluster中理解变量和方法的作用范围和生命周期很困难，如果RDD总是在射程外操作变量是个大麻烦

- example：使用foreach()方法增加一个计数器【其他方法类似】

```
counter = 0
rdd = sc.parallelize(data)

# 错误示例！！！
def increment_counter(x):
    global counter
    counter += x
rdd.foreach(increment_counter)

print("Counter value: ", counter)
```
- 闭包原理描述（对于cluster）
![](http://ww1.sinaimg.cn/large/005N2p5vgy1fp5fy3jcw0j31i81640v3.jpg)

- 对于local mode，functions使用和driver一样的jvm，所以会参考同一个original counter,并且会对这个counter进行实际的更新


### 以下场景可以使用Accumulator
- accumulator是spark专门用于 
    - 当execution被分割在不同节点
    - 安全的更新变量
- 具体参见Accumulator笔记

## 闭包适合的应用场景
- 闭包结构体更像 循环 或者 本地定义的方法，而不适合做 全局状态的 更改
- spark不能保证对全局状态的更改是在闭包之外
- 上述的错误代码意外的可以在local上work，但是cluster情况会运行出错


## 总结
>对全局变量的更改，不适合闭包，更适合Accumulator处理 \
>对于local运行的循环或者方法，可以使用闭包在executor间进行传递


