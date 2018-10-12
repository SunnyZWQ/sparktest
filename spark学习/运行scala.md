
### sbt项目的目录结构

    [elon@hadoop wordcount]$ find .
    .
    ./src
    ./src/main
    ./src/main/scala
    ./src/main/scala/SimpleApp.scala
    ./simple.sbt

### simple.sbt

此文件用于规定环境中Spark及Scala版本。

可通过 ./spark-shell 查看Spark及Scala版本。

simple.sbt文件的内容如下：

    name := "Simple project"
    version :="1.0"
    scalaVersion :="2.11.8"
    libraryDependencies +="org.apache.spark" %% "spark-core" % "2.1.0"

spark代码在SimpleApp.scala文件中，内容如下：

    import org.apache.spark.SparkContext
    import org.apache.spark.SparkContext
    import org.apache.spark.SparkConf

    object SimpleApp {
    def main(args: Array[String]) {
        val logFile = "/user/spark/README.md"
        val conf = new SparkConf().setAppName("Simple Application")
        val sc = new SparkContext(conf)
        val logData = sc.textFile(logFile, 2).cache()
        val numAs = logData.filter(line => line.contains("a")).count()
        val numBs = logData.filter(line => line.contains("b")).count()
        println("LInes with a: %s, Lines with b: %s".format(numAs, numBs))
      }
    }

### 使用sbt编译scala代码生成jar包

    sbt package

第一次执行时会下载很多依赖，因此会运行很久。

### 生成的目录结构

![](http://ww1.sinaimg.cn/large/005N2p5vly1fw59sljan1j30c001gdfv.jpg)

编译好的jar包位于

    ./target/scala-2.11/simple-project_2.11-1.0.jar

### 在spark中提交任务

    ./spark-submit \
      --class SimpleApp\
      --master yarn \
      /usr/sparkapp/testsbt/target/scala-2.11/simple-project_2.11-1.0.jar

