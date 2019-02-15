## 概述

并发编程辅助包

### 搬运工

并行获取数据。数据源可是RPC服务，也可是数据库

TODO

### 发令枪

当所有任务都准备好时才开始执行。

可用于模拟并发条件下产生的竞争环境。

使用方法：

```java
//方法一：无参构造函数
class Demo{
    //使用无参构造函数，需要先调用`addAthlete()`方法添加要执行的任务
    //最后调用`run()`方法，开始执行。
    //注意：`run()`方法必须调用
    public static void main(String[] args){
        StartingGun startingGun = new StartingGun();
        startingGun.addAthlete(()->{
            //do something
        }).addAthlete(()->{
            //do something
        }).run();  
    }
}

//方法二：有参构造函数
class Demo{
    //使用有参构造函数，只需调用`addAthlete()`方法即可。达到构造函数传入参数的上限，自动执行。
    //多余调用的`addAthlete()`方法，无任何效果，不被执行。
    //注意：`run()`方法可不调用
    public static void main(String[] args){
        StartingGun startingGun = new StartingGun(2);
        startingGun.addAthlete(()->{
            //do something
        }).addAthlete(()->{
            //do something
        }).addAthlete(()->{  //多余调用的，不会被执行
            //do something
        });  
    }
}
```


