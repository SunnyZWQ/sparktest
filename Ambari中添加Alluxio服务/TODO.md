# 代码完成进度

- [x] 在master和slave节点完成各自安装步骤
    - [x] 版本兼容
    - [ ] 完全自动化（可能需要打包，不了解OpenStack，后续和同事交流）
- [x] 在各个节点上安装成功后，为两个节点分发master、slave的IP
    - [x] 在每个节点都能够获取ambari server的hostname
    - [x] 使用REST API获取集群名字
    - [x] 使用REST API获取alluxio_master hostname
    - [x] 使用REST API获取alluxio_slave hostname
    - [x] 解析REST API传递的json字符串
    - [x] 追加master worker文件内容
- [ ] 集成spark
- [x] 集成HDFS
- [ ] 学习blueprint（一键配置）
- [ ] 继续发现不完善的地方

------

- [x] 核心功能代码完成
- [ ] 集成spark需要手动写路径，准备写文档（翻译Alluxio官网）
- [ ] 界面显示的bug
- [ ] 日志位置bug

- [ ] 源码架构梳理清楚
- [ ] 

------

# 遇到问题

- [ ] 调试时需要不停重新安装，很浪费时间。如果使用单元测试，则无法获取ambari运行时的信息
    - [ ] 查看blueprint看是否能找到方法方便调试
- [ ] ambari的信息比较少，官方没给出，也google不到，有些配置信息需要自己查文件。
- [ ] 对ambari运行方式不了解，在学习源码



























































