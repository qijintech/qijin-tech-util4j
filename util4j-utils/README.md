## 作用

各种工具类

### log

日志格式化工具

为了避免不同人员打印日志习惯不一样，导致日志展示样式五花八门，这里使用`LogFormat`工具类统一日志输出格式。

同时，如果日志格式需要改动，修改工具类后，所有系统均会更改

**使用方式**
```java
log.info(LogFormat.builder()
        .put("a", 1)
        .put("b", "hehe")
        .put("c", object)
        .message("this is a message")
        .build());
```

其中：

`message`: 用于填写日志描述，即日志的内容
`put()`: 建议用于输出参数的值。

注：

开头的`builder()`和结尾的`build()`是必需的，其他的均非必需。

日志输出示例：

```
2018-11-13 16:43:44,482 [INFO ][REQUEST] - [duration=27 ms][request=modelId=123&asdfasdf=adsf][httpStatus=200][uri=/1/model/list]
```