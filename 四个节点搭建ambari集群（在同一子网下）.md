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


3. 将节点1，2，3的密钥拷贝到节点0中的authorized_keys文件中

在1，2，3中执行如下代码

    cat ~/.ssh/id_rsa.pub | ssh root@zwq0 'cat >> ~/.ssh/authorized_keys'
![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpkhyuu6f2j31ks13i7lk.jpg)


4. 将zwq0的authorized_keys文件分发到所有节点上面，在节点0上执行如下代码

        cd ~/.ssh
        scp -r authorized_keys root@zwq1:~/.ssh/
        scp -r authorized_keys root@zwq2:~/.ssh/
        scp -r authorized_keys root@zwq3:~/.ssh/

![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpki2vy5a9j31ks13i1d2.jpg)


## 安装JDK（操作节点：0，1，2，3）

1. 下载JDK

OpenJDK经常会出现各种问题，所以安装Oracle JDK，在[官网](http://www.oracle.com/technetwork/java/javase/downloads/index.html)下载压缩包

![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpkibn33m3j30um0h444i.jpg)

下载后，使用xftp（Windows）或者scp（Mac）传输到四个节点上

在四个节点新建文件夹，用于安装JDK

    mkdir ~/java

scp指令如下：

    scp jdk-8u161-linux-x64.tar.gz root@10.0.217.93:~/java/


![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpkixgm4mqj327m0j0qfl.jpg)

2. 安装JDK（操作节点：0，1，2，3）

        cd ~/java
        tar -zxvf jdk-8u161-linux-x64.tar.gz

![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpkj2qo5dsj322e12w1l0.jpg)

3. 配置Java环境变量

        vim /etc/profile

追加如下内容：
        
    JAVA_HOME=/root/java/jdk1.8.0_161
    CLASSPATH=.:$JAVA_HOME/lib.tools.jar
    PATH=$JAVA_HOME/bin:$PATH
    export JAVA_HOME CLASSPATH PATH

![](http://ww1.sinaimg.cn/large/005N2p5vly1fpkj5hat1fj31ks13i12l.jpg)

使环境变量生效：

    source /etc/profile

验证是否配置成功：

    java -version

![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpkj8fcyh6j31ks13iaqy.jpg)

![](http://ww1.sinaimg.cn/large/005N2p5vgy1fpkjcvpgfwj321u11ynpf.jpg)


## 下载ambari及HDP库（操作节点：0）

[ambari-2.6.1.0](http://public-repo-1.hortonworks.com/ambari/centos7/2.x/updates/2.6.1.0/ambari-2.6.1.0-centos7.tar.gz)

[HDP-2.6.4.0](http://public-repo-1.hortonworks.com/HDP/centos7/2.x/updates/2.6.4.0/HDP-2.6.4.0-centos7-rpm.tar.gz)

[HDP-UTILS-1.1.0.22](http://public-repo-1.hortonworks.com/HDP-UTILS-1.1.0.22/repos/centos7/HDP-UTILS-1.1.0.22-centos7.tar.gz)

[如果使用的不是centos7，点击此处跳转到官网查看下载地址](https://docs.hortonworks.com/HDPDocuments/Ambari-2.6.1.0/bk_ambari-installation/content/ambari_repositories.html)



## 安装ambari（操作节点：0）

### 准备工作












