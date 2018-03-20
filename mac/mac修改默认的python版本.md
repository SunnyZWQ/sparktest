# mac下安装新的Python，并指定为默认的Python

### 安装新的Python
由于mac自带的Python库有问题，在用Python配置环境时可能会出问题，因此需要自己手动安装新的Python。

#### 使用Homebrew重新安装Python
homebrew官网：https://brew.sh/index_zh-cn.html \
在终端执行如下指令

    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

这样就安装好了Homebrew，接下来用homebrew安装Python，执行指令：

    brew install python

#### 更改mac默认的Python版本

安装好Python之后，执行python看到mac启动的默认Python还是本机自带的。这时就需要把默认Python更改为新安装的Python

新的Python的安装路径是 /usr/local/bin/python3 \
也可能和我的安装地址和Python版本不同。自己的安装地址在之前执行指令

    brew install python

之后，命令行会出现如下字符

    ######################################################################## 100.0%
    ==> Pouring python-3.6.4_4.high_sierra.bottle.tar.gz
    ==> /usr/local/Cellar/python/3.6.4_4/bin/python3 -s setup.py --no-user-cfg install --force --verbose
    ==> /usr/local/Cellar/python/3.6.4_4/bin/python3 -s setup.py --no-user-cfg install --force --verbose
    ==> /usr/local/Cellar/python/3.6.4_4/bin/python3 -s setup.py --no-user-cfg install --force --verbose
    ==> Caveats
    Python has been installed as
    /usr/local/bin/python3

    Unversioned symlinks `python`, `python-config`, `pip` etc. pointing to
    `python3`, `python3-config`, `pip3` etc., respectively, have been installed into
    /usr/local/opt/python/libexec/bin

    If you need Homebrew's Python 2.7 run
    brew install python@2

    Pip, setuptools, and wheel have been installed. To update them run
    pip3 install --upgrade pip setuptools wheel

    You can install Python packages with
    pip3 install <package>
    They will install into the site-package directory
    /usr/local/lib/python3.6/site-packages

    See: https://docs.brew.sh/Homebrew-and-Python
    ==> Summary

其中的

    Python has been installed as
    /usr/local/bin/python3
就是我们需要的了。

将这个路径复制下来。

执行命令：

    vim ~/.bashrc
这是个新文件也没关系。
在文件中增加如下内容：

    alias python='/usr/local/bin/python3'

这里面的路径根据你自己的路径填写
修改完毕之后，保存退出，在命令行执行：

    . ~/.bashrc
使刚刚修改的文件生效。

再验证Python的版本

    python

这时显示

    zhangwenqingdeMacBook-Pro:~ zhangwenqing$ python
    Python 3.6.4 (default, Mar  9 2018, 23:15:03) 
    [GCC 4.2.1 Compatible Apple LLVM 9.0.0 (clang-900.0.39.2)] on darwin
    Type "help", "copyright", "credits" or "license" for more information.
    >>> 


修改完毕~~