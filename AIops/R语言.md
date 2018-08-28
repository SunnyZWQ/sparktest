    > age <- c(1,3,5,2,11,9,3,9,12,3)
    > weight <- c(4.4,5.3,7.2,5.2,8.5,7.3,6.0,10.4,10.2,6.1)
    > mean(weight)
    [1] 7.06
    > sd(weight)
    [1] 2.077498
    > cor(age,weight)
    [1] 0.9075655
    > plot(age,weight)

c() 向量

mean() 平均值

sd() 标准差

cor() 相关度

plot() 散点图

![](http://ww1.sinaimg.cn/large/005N2p5vly1fuimmkhihhj32801e0dl5.jpg)


    > getwd()
    [1] "/Users/zhangwenqing"
    > dir.create("/Users/zhangwenqing/Rexamples")
    > dir.create("/Users/zhangwenqing/Rexamples/project1")
    > setwd("/Users/zhangwenqing/Rexamples/project1")

getwd() 获取当前工作目录

dir.create("")创建目录

setwd() 指定当前工作目录


    > x <- runif(20)
    > summary(x)
    Min. 1st Qu.  Median    Mean 3rd Qu.    Max. 
    0.03815 0.23265 0.50841 0.53079 0.90680 0.98956 
    > hist(x)


![](http://ww1.sinaimg.cn/large/005N2p5vly1fuimotb6k7j32801e0wjq.jpg)

runif(20) 创建一个包含20个均匀分布随机变量的向量

summary(x) 生成向量x摘要

hist(x) 生成向量x的直方图 

    > savehistory()
    > save.image()
    > 
    > q()
    Save workspace image? [y/n/c]: y
    zhangwenqingdeMacBook-Pro:~ zhangwenqing$ 

命令的历史记录保存到.Rhistory中

工作空间（包含向量x）保存到文件R.Data中，会话结束

>在启动一个R会话时使用setwd()命令指定到某一个项目的路径，后接不加选项的load()命令。这样做可以让我从上一次会话结束的地方重新开始，并保证各个项目之间的数据和设置互不干扰。

![](http://ww1.sinaimg.cn/large/005N2p5vly1fuo9pqb8etj31zo136k4u.jpg)

    source("filename.R")

在当前会话中执行一个脚本filename.R。扩展名.R不是必须的。

    sink("filename")

将输出重定向到文件filename中。如果文件已经存在，则它的内容将被覆盖。

    参数 append=TRUE 可将文本追加到文件后。
    参数 split=TRUE 可将输出同时发送到屏幕和输出文件中。
    不加参数调用sink()将仅向屏幕返回输出结果。


![](http://ww1.sinaimg.cn/large/005N2p5vly1fuo9xnguwsj31zm0l4q7h.jpg)


































































