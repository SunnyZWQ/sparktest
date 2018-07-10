参考：http://www.baeldung.com/maven


# 1 简介

#### build工程通常需要：
- 下载依赖
- 将添加的jar包放在classpath上
- 将源码编译为binary code
- 运行测试
- 将编译好的代码打包为可部署的artifacts
    - 如：JAR, WAR, and ZIP files
- 将这些artifacts部署到应用的服务器上或者repository上


#### maven的工作：
- 自动化build工程的任务，减小人工出现错误的风险
- 将工作分割为编译部分和打包部分


#### 为什么使用maven：
- 简单的工程安装——通过提供项目模板，尽可能多地避免人工配置
- 依赖管理——自动更新、下载、验证兼容性，报告dependency closures（也称作transitive dependencies）
- 隔离项目依赖和插件——依赖从dependency repositories中检索，插件从plugin repositories中检索，这样可以在插件下载额外的依赖时减少冲突



# 2 POM（Project Object Model）

- maven项目的配置由POM完成，POM在pom.xml文件中声明。

        <project>
            <modelVersion>4.0.0</modelVersion>
            <groupId>org.baeldung</groupId>
            <artifactId>org.baeldung</artifactId>
            <packaging>jar</packaging>
            <version>1.0-SNAPSHOT</version>
            <name>org.baeldung</name>
            <url>http://maven.apache.org</url>
            <dependencies>
                <dependency>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                    <version>4.12</version>
                    <scope>test</scope>
                </dependency>
            </dependencies>
            <build>
                <plugins>
                    <plugin>
                    //...
                    </plugin>
                </plugins>
            </build>
        </project>

### 2.1 项目标识符

- groupId – a unique base name of the company or group that created the project
- artifactId – a unique name of the project
- version – a version of the project
- packaging – a packaging method (e.g. WAR/JAR/ZIP)


### 2.2 Dependencies

- dependencies——项目使用的额外的库
- Maven中的依赖管理功能保证了这些库能够从central repository中下载。所以不需要在本地保存这些库。

#### 使用Dependencies的优点：

- 通过显著减少远程repositories的下载量来使用更少的存储
- 更快地检查项目
- 在团队中更有效地转换binary artifacts，避免每次都要通过源码构建artifact。


        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>4.3.5.RELEASE</version>
        </dependency>

As Maven processes the dependencies, it will download Spring Core library into your local Maven repository.


### 2.3 Repositories

- Repository是用来保存构建好的artifacts和各种类型的dependencies。
- 默认的本地repository是 ${user.home}/.m2/repository
    - 如：/Users/zhangwenqing/.m2/repository
- 如果一个artifact或者plug-in在本地库中可用，Maven就会使用。否则，则会从central repository下载，并保存在本地repository，默认的central repository是Maven Central（http://search.maven.org/#search|ga|1|centra）
- 如果有些库不在central repository中，但是在另一个可选的repository中，你可以在pom.xml文件中提供可选repository的URL，来下载这些库。

>Some libraries, such as **JBoss server**, are not available at the central repository but are available at an alternate repository.For those libraries, you need to provide the URL to the alternate repository inside pom.xml file:

    <repositories>
        <repository>
            <id>JBoss repository</id>
            <url>http://repository.jboss.org/nexus/content/groups/public/</url>
        </repository>
    </repositories>

- 在一个项目中可以使用多个repositories


### 2.4 Properties

在properties标签中配置的属性，可以在其他标签中引用。\
提升了可读性和可扩展性。

    <name>xxx</name>   --->   ${name},

详细代码如下：

    <properties>
        <spring.version>4.3.5.RELEASE</spring.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
    </dependencies>

一般可用properties指定**版本**和**路径**：

    <properties>
        <project.build.folder>${project.build.directory}/tmp/</project.build.folder>
    </properties>
    
    <plugin>
        //...
        <outputDirectory>${project.resources.build.folder}</outputDirectory>
        //...
    </plugin>



### 2.5 Build

Build是一个Maven POM中非常重要的部分。

Build提供的信息：

- the default Maven goal
- the directory for the compiled project
- the final name of the application

默认的build代码如下：

    <build>
        <defaultGoal>install</defaultGoal>
        <directory>${basedir}/target</directory>
        <finalName>${artifactId}-${version}</finalName>
        <filters>
        <filter>filters/filter1.properties</filter>
        </filters>
        //...
    </build>

对于编译好的artifact，默认的输出文件夹命名为target

### 2.6 使用profiles（概述）

- 一个profile的基本组成是一系列的配置值
- 通过使用profiles，可以为不同的环境自定义如何build
    - 如：Production/Test/Development

            <profiles>
                <profile>
                    <id>production</id>
                    <build>
                        <plugins>
                            <plugin>
                            //...
                            </plugin>
                        </plugins>
                    </build>
                </profile>
                <profile>
                    <id>development</id>
                    <activation>
                        <activeByDefault>true</activeByDefault>
                    </activation>
                    <build>
                        <plugins>
                            <plugin>
                            //...
                            </plugin>
                        </plugins>
                    </build>
                </profile>
            </profiles>

- 如果想运行production profile，使用如下指令：

        mvn clean install -Pproduction

# 3 Maven Build Lifecycles

Maven build遵循着特定的生命周期。

可以执行几个build lifecycle goals，包括compile项目源码、打包、并在本地Maven dependency repository中安装archive file。

### 3.1 Lifecycle Phases

- validate – checks the correctness of the project
- compile – compiles the provided source code into 
binary artifacts
- test – executes unit tests
- package – packages compiled code into an archive file
- integration-test – executes additional tests, which require the packaging
- verify – checks if the package is valid
- install – installs the package file into the local Maven repository
- deploy – deploys the package file to a remote server or repository

**source code**  ----compile---->  **binary artifacts** ----package---->  **archive file**


### 3.2 Plugins and Goals

- A Maven plugin is a collection of one or more goals.

- Goals在阶段内处理，这样有利于决定goals执行的顺序。

- 如果要执行以上phases，可输入以下命令

        mvn <phase>

>例如：*mvn clean install* will remove the previously created jar/war/zip files and compiled classes (clean) and execute all the phases necessary to install new archive (install).
































