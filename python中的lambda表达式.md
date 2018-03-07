# spark demo运行（spark-shell）


## aggregate函数官方文档的测试

```
>>> 
>>> seqOp = (lambda x,y: (x[0] + y, x[1] + 1))
>>> combOp = (lambda x,y: (x[0] + y[0], x[1] + y[1]))
>>> sc.parallelize([1,2,3,4]).aggregate((0,0), seqOp, combOp)
(10, 4)
>>>
>>>
>>> sc.parallelize([]).aggregate((0, 0), seqOp, combOp)
(0, 0)
```

- seqOp——生成二元组
```
>>> data = sc.parallelize([1,2,3,4])
>>> data.map(lambda x,y:(x[0]+y, x[1]+1))
PythonRDD[9] at RDD at PythonRDD.scala:48
>>> data.reduce(lambda x,y:[x,y])
[[1, 2], [3, 4]]
```
- combOp（**unfinished**）
```
```

## 两个表达式（not understand yet）
- 第二个表达式不是输入

```
>>> reduce(lambda x,y:[x,y], [1,2,3,4])
[[[1, 2], 3], 4]
```
- 第二个表达式是输入
```
>>> reduce(lambda x,y:x+y,[1,2,3])
6
```
```
>>> sum = sc.parallelize([1,2,3])
>>> sum.reduce(lambda x,y:x+y)
6
```
- 两个表达式的lamdba函数demo（第二个不是输入）
```
>>> reduce(lambda x,y:x+y*2, [1,2])
5
>>> reduce(lambda x,y:x+y*2, [1,2,3])
11
>>> reduce(lambda x,y:x+y*2, [1,2,3,4])
19
```
```
>>> sum = sc.parallelize([1,2,3])
>>> sum.reduce(lambda x,y:x+y*2)
17
```
- 多个元素生成二元组
```
>>> data = sc.parallelize([1,2,3,4,5,6])
>>> data.map(lambda x,y:(x[0]+y, x[1]+1))
PythonRDD[15] at RDD at PythonRDD.scala:48
>>> data.reduce(lambda x,y:[x,y])
[[[1, 2], 3], [[4, 5], 6]]
>>> 
>>> 
>>> 
>>> data = sc.parallelize([1,2,3,4,5,6,7,8])
>>> data.map(lambda x,y:(x[0]+y, x[1]+1))
PythonRDD[18] at RDD at PythonRDD.scala:48
>>> data.reduce(lambda x,y:[x,y])
[[[[1, 2], 3], 4], [[[5, 6], 7], 8]]
```
```
>>> data=sc.parallelize([1,2,3,4])
>>> data.map(lambda x,y: x*y)
PythonRDD[21] at RDD at PythonRDD.scala:48
>>> data.reduce(lambda x,y:[x,y])
[[1, 2], [3, 4]]
>>> data.collect()
[1, 2, 3, 4]
```
