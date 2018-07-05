参考：
https://blog.csdn.net/chengyuqiang/article/details/60963480


# 1 基本概念

1. Resource：Ambari把对资源的管理抽象成为一个Resource实例，资源可以包括服务、组件、主机节点等。一个resource实例中包含了一系列该资源的属性；
2. Property：服务组件的指标名称
3. ResourceProvider和PropertyProvider的提供方。获取指标需首先获取Resource，然后获取Property对应的metric
4. Query：Resource的内部对象，代表了对该资源的操作。
5. Request：对Resource的操作请求，包含http信息以及要操作的Resource的实例。Request按照http的请求方式分为四种：GET、PUT、DELETE、POST；
6. Predicate：一个Predicate代表一系列表达式，如and、or等


# 2 基本组件

- Ambari Server——【Jetty、Spring、JAX-RS等】提供REST接口给Agent和web访问。用户不需要通过界面，而是通过curl命令来操控集群。
- Ambari Agent——【puppet管理节点,rubby,facter,nagios,ganglia】在每台机器上部署，Agent主要负责用来接收来自Server的命令（如：安装、启动、停止Hadoop集群中的某一个服务）
- Ambari Web——【采用ember.js作为前端MVC框架和NodeJS相关工具，用handlebars.js作为页面渲染引擎，在CSS/HTML方面还用了Bootstrap框架。 】
- Ambari-metrics-collector——监察集群性能Master：在Ambari所管理的集群中用来收集、聚合和服务Hadoop和系统计量
- Ambari-metrics-monitor——监察集群性能Slave
- Ambari-views——用于扩展Ambari Web UI中的框架
- Contrib——自定义第三方库
- Docs——文档


# 3 技术栈

#### Ambari Server

- Server code: Java 1.7 / 1.8
- Agent scripts: Python
- Database: Postgres, Oracle, MySQL
- ORM: EclipseLink
- Security: Spring Security with remote 
- LDAP integration and local database
- REST server: Jersey (JAX-RS)
- Dependency Injection: Guice
- Unit Testing: JUnit
- Mocks: EasyMock
- Configuration management: Python

#### Ambari Web
- Frontend code: JavaScript
- Client-side MVC framework: Ember.js / AngularJS
- Templating: Handlebars.js (integrated with Ember.js)
- DOM manipulation: jQuery
- Look and feel: Bootstrap 2
- CSS preprocessor: LESS
- Unit Testing: Mocha
- Mocks: Sinon.js
- Application assembler/tester: Brunch / Grunt / Gulp


## TODO：Ambari源码调用过程（从启动开始）
























