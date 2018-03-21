## 在四个节点上搭建ambari集群(ambari版本：2.6.1.0)（HDP版本：2.6.4）（在同一子网下）（centos7.3 64bit）

环境：四个节点，都是centos7.3 64bit操作系统

主机名分别为 zwq0 zwq1 zwq2 zwq3

zwq0作为master，其余的作为slave

**在本文中，为了方便，简称为主机0，1，2，3**

## 配置网络环境

### 1. 修改主机名（操作主机：0，1，2，3）

    vim /etc/sysconfig/network
    
在文件中追加：
    
    HOSTNAME=zwq0

完成之后主机名会有变化，如下图
![](http://ww1.sinaimg.cn/large/005N2p5vly1fpkf04ynn1j321q11wqv7.jpg)

如果没有变化，执行

    reboot
重启主机之后就能看到了

### 2. 关于用户组

这个集群是我平时自己用来学习、测试用的。\
既然只有自己一个人用，就没有必要设置用户组来限制其他人使用的权限。\
所以，接下来的ambari用户就是root了~~

### 3. 设置集群主机的内网地址
首先获取本机的内网地址

    ifconfig
![](http://ww1.sinaimg.cn/large/005N2p5vly1fpkfaywo29j31ks13i190.jpg)

对主机0，1，2，3都执行相同的指令，并记录每一台主机的内网地址。

接下来，设置/etc/hosts（操作主机：0,1,2,3）

    vim /etc/hosts

追加集群内所有主机的地址
![](http://ww1.sinaimg.cn/large/005N2p5vly1fpkffitbzxj31ks13i43u.jpg)


### 4. 关闭防火墙（操作主机：0，1，2，3）
查看防火墙状态

    service iptables status

临时关闭防火墙

    service iptables stop

永久关闭防火墙（推荐）

    chkconfig iptables off

### 5. 关闭selinux（操作主机：0，1，2，3）

    vim /etc/selinux/config

![](http://ww1.sinaimg.cn/large/005N2p5vly1fpkfmo94nsj31j811yn4r.jpg)


## ambari需要安装的软件

- yum
- rpm
- scp, curl, unzip, tar, and wget
- centos7需要安装2.7.x版本的Python
- JDK：如果要安装HDP2.6.4，官网要求：
    - Open Source JDK：JDK8+
    - Oracle JDK：默认JDK8,64bit，最小JDK 1.8.0_77
- 数据库：官网说明：在安装ambari时，会默认在ambari server上安装PostgreSQL，ambari支持自动安装的Postgres数据库，但你也可以选择其他数据库

官网声明的ambari支持的数据库如下：
![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpkgztkol0j30xq0j4td4.jpg)

为了方便，我不再额外安装数据库，仅使用ambari默认安装的数据库。

如果想使用已经存在的数据库，可[点此查看官网教程](https://docs.hortonworks.com/HDPDocuments/Ambari-2.6.1.5/bk_ambari-administration/content/using_existing_databases_-_ambari.html)

- ambari组件的大小

![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpkh6jz9c0j31040eigod.jpg)

- 安装相关服务
        
        yum -y install lrzsz
        yum install -y openssh-clients


## 设置SSH免密登录

1. 生成SSH密钥（操作主机：0，1，2，3）

        cd ~
        mkdir .ssh
        ssh-keygen -t rsa

        cd .ssh
        ls
        cat id_rsa.pub >> authorized_keys
        ls
        cd ..
        chmod 700 .ssh
        chmod 600 .ssh/*

注意：在输入密码时，一律按回车键，这样密码就会设为空

2. 验证SSH免密登录是否成功：
    
        ssh zwq0
第一次登录时需要输入yes，之后就不用了~~

其余的3个节点都进行同样的操作

![](http://ww1.sinaimg.cn/large/005N2p5vly1fpkho8zo2dj316k14i1ky.jpg)


验证成功后，输入
    
    exit
退出SSH连接（操作主机：0，1，2，3）




