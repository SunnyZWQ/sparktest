参考：

- https://blog.csdn.net/cun_chen/article/details/51534036
- https://www.cnblogs.com/xujian2014/p/5551153.html
- https://blog.csdn.net/briblue/article/details/54973413

>ClassLoader的具体作用就是将class文件加载到jvm虚拟机中去，程序就可以正确运行了。但是，jvm启动的时候，并不会一次性加载所有的class文件，而是根据需要去动态加载。一次性加载那么多jar包那么多class，那内存会崩溃。因此有了ClassLoader这种加载机制。

# 1 java类加载机制（加载class文件）

- java类加载器——一部分JRE加载JAVA CLASSES到 JVM。
- 懒加载——使用ClassLoader实现，并且使得JVM不必关心加载文件以及所使用的文件系统。

#### java编译过程：
    javac my.java   --->   生成my.class
    java my.class   --->   编译运行

# 2 java类加载过程（java语言自带的三个类加载器）

![](http://ww1.sinaimg.cn/large/005N2p5vly1fsxmm2m1z9j30cf06imx4.jpg)

- Bootstrap ClassLoader——最顶层的加载类，主要加载核心类库，%JRE_HOME%\lib下的rt.jar、resources.jar、charsets.jar和class等。

- Extention ClassLoader——扩展的类加载器，加载目录%JRE_HOME%\lib\ext目录下的jar包和class文件。还可以加载-D java.ext.dirs选项指定的目录。 

- Appclass Loader/SystemAppClass——加载当前应用的classpath的所有类。


TODO：源码分析（参考中的第三篇）






































