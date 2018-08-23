

# 1 TODO

- [x] 如何raise alarm
- [x] 如何定义high CPU等问题
- [ ] vitrage如何与OpenStack组件关联（多个host、多个instance等情况）



# 2 概述


## 2.1 统计学习中的RCA算法原理

在实现RCA的算法中，主要思路是首先构造一棵故障树，其本质是决策树，故障树中的节点是物理实体，如主机、交换机等，节点间的连接关系是靠物理关系和逻辑关系。在构建故障传播链时，可能只需要树中的一段，因此要对初始构造的决策树进行剪枝，只留下故障传播链部分。传统的决策树算法中，其算法目的是分类，剪枝的理论也是为了得到具有清晰分类的叶子节点，但在RCA中，剪枝的理论依据是各个节点之间的关联关系——如果故障节点与另一节点没有关联或者关联关系小于指定的阈值，则可以将此节点剪掉（该节点的子节点也会消失），至于如何获取关联关系是通过时序分析算法中的联动分析。

## 2.2 vitrage的功能



**vitrage的功能是将物理实体映射到virtrage的Graph中：**

通过配置vitrage环境变量，将其与Nova等OpenStack组件关联起来，即可获取Nova的host、instance等组件信息，并将其映射为Graph的节点，节点关系由人在template文件中定义。构建好Graph之后，再由Evaluator组件对其进行RCA分析。在RCA算法中是树，但这里是图，是因为Graph中的节点不只是物理实体，还有Alarm，Causes等，Alarm_on_instance表示instance上有报警，A_causes_B表示A是B的根因。

![](http://ww1.sinaimg.cn/large/005N2p5vly1fua8dw1syaj30mm09tq3g.jpg)




## 2.3 组件介绍

![](http://ww1.sinaimg.cn/large/005N2p5vly1fua5qe8mo2j31t6168ag1.jpg)

#### Graph——用图表示的库

- Graph包含顶点和边。顶点是Resources(entity,alarm)，边是relationship(on,causes)
- Graph Driver负责对Graph的操作：对顶点和边的CRUD。

![](http://ww1.sinaimg.cn/large/005N2p5vly1fua8d4k9dzj31my16q7ev.jpg)

![](http://ww1.sinaimg.cn/large/005N2p5vly1fua8dw1syaj30mm09tq3g.jpg)

![](http://ww1.sinaimg.cn/large/005N2p5vly1fua8eau2pbj30jn0eidgl.jpg)

#### DataSource

一张图可有多个数据源。数据源定义了需要导入组件的那些entity, alarm, 以及如何将这些信息集成到Graph中。

- entity : physical and virtual resources
- alarm : Aodh, Nagios, Monasca, etc


datasource包含的组件:
- driver
- transformer

# 3 Vitrage工作流程

构建Graph--->对Graph分析（推断alarm，RCA）

## 3.1 构建Graph

Datasource中的driver从OpenStack组件（nova）中检索所有的entities(nova.host)，并将其放入entity queue中。entity processor从entity queue中对entity进行轮询，对每个entity event调用对应entity类型的transformer，以理解如何将检索到的信息集成到Graph中。最后，entity processor通过Graph Driver的CRUD API构建或者更新Graph（增加顶点和对应的边）。

![](http://ww1.sinaimg.cn/large/005N2p5vly1fuae44sptcj30jx0a5wgx.jpg)


## 3.2 根据Graph进行推断

vitrage如何进行报警预测及RCA——一种if-then模式。由用户指定模板中的方案，如果当前Graph满足此方案的条件，则可从方案中获取哪里将会报警或者造成这种情况的根因是什么，并根据这个方案作出后续操作。

如：Nova host如果有报警，说明有故障，那么就可以推断host里的Nova instance也将会有错。

![](http://ww1.sinaimg.cn/large/005N2p5vly1fucq5akv66j31go0trh5t.jpg)

# 高可用

Vitrage的高可用

# 改进

Vitrage的templates只在开启vitrage服务时加载进来，这样进行分析时无法实时添加RCA Graph，改进建议：通过实时的算法分析RCA之后，改写vitrage代码，使其能够重新加载templates或者重新加载templates中的senioras，这样能够实现实时。










------

#### processor

1. handling events
2. Reconstructing the graph from the historic data
































