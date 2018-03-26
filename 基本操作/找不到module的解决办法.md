sudo cp -R /Library/Python/2.7/site-packages/sphinx_rtd_theme  /usr/local/lib/python3.6/site-packages/

# 找不到module的解决办法

进入Python shell
    
    import sys
    sys.path

将module copy到path中

或者将默认的module下载路径写入path中



    >>> import sys
    >>> sys.path
    ['', '/usr/local/Cellar/python/3.6.4_4/Frameworks/Python.framework/Versions/3.6/lib/python36.zip', '/usr/local/Cellar/python/3.6.4_4/Frameworks/Python.framework/Versions/3.6/lib/python3.6', '/usr/local/Cellar/python/3.6.4_4/Frameworks/Python.framework/Versions/3.6/lib/python3.6/lib-dynload', '/usr/local/lib/python3.6/site-packages']