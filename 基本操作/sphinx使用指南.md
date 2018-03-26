# 将已有的文档库 Git clone 到本地之后的编写（sphinx 1.7.1）

### 初始化

执行

    make html

生成 build 目录

打开浏览器，输入网址

    file:///Users/zhangwenqing/Documents/uos1/ubd/build/html/index.html#
（根据自身情况进行修改）

即可看到网页版文档。


### 如何书写

比如，需要添加ambari-deploy.rst文档。

1. 在source目录下创建ambari-deploy.rst文件并编写。为了测试，先写个标题。我的ambari-deploy.rst文件内容如下：

        6 ambari 部署
        ==================
2. 在source目录下的index.rst中编写如下内容：

        .. toctree::
        :maxdepth: 2

        ambari-deploy

3. 保存之后，执行：

        make html
这时刷新网页，就能看见新的内容了。

![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpqc9og44hj328018s0wu.jpg)

注：我使用的主题是公司开发的，所以和大家的不一样。不要介意~~

