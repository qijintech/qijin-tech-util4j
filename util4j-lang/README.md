
## ICacheKey

`ICacheKey`用做不同模块下生成缓存的key。

`ICacheKey`是个接口，各模块的实现类希望是个enum类型。

### 使用步骤

Step1: 创建一个enum类，并实现`ICacheKey`接口

```java
public enum TestCacheKey implements ICacheKey{
    @Override
    public String module() {
        return "";
    }
}
```

Step2: `module()`方法中返回代表模块的字符串 

```java
public enum TestCacheKey implements ICacheKey{
    @Override
    public String module() {
        return "game";
    }
}
```

Step3： 在enum中创建任意一个实例

```java
public enum TestCacheKey implements ICacheKey{
    INSTANCE;
    
    @Override
    public String module() {
        return "game";
    }
}
```

Step4: 调用`key()`方法创建cache key

```java
TestCacheKey.INSTANCE.key("gameId", "modleId");
```

注：
* 使用enum类，是为了保证CacheKey是单例的
* `key()`方法接受String数组作为参数。每个String之间用`.`分割。
* 建议在各模块的CacheKey实现类中，添加足够的、有意义的方法，而不直接使用`key()`方法。如：
```java
public enum TestCacheKey implements ICacheKey{
    private static final String GAME_PREFIX = "game";
    private static final String GRADE_PREFIX = "grade";
    private static final String MODEL_PREFIX = "model";
    
    public String getGameKey(long gameId){
        return key(GAME_PREFIX, String.valueOf(gameId));    
    }
    
    public String getGradeKey(long gradeId){
        return key(GAME_PREFIX, GRADE_PREFIX, String.valueOf(gameId));    
    }
    public String getModelKey(long modelId){
        return key(GAME_PREFIX, MODEL_PREFIX, String.valueOf(gameId));    
    }
}

```

