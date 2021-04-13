### Singleton 单列模式

#### 为什么要使用单例？

`单例设计模式`（Singleton Design Pattern）理解起来非常简单。

一个类只允许创建一个对象（或者实例），那这个类就是一个单例类。

使用场景：

> - 避免资源访问冲突
> - 表示全局唯一的类



#### 如果实现一个单例？

实现一个单例，需要关注的点有以下几个方面：

> - 构造函数需要是 private 访问权限的，这样才能避免外部通过 new 创建实例；
> - 考虑创建对象的线程安全问题；
> - 考虑是否支持延迟加载；
> - 考虑 getInstance() 性能是否高（是否加锁）;



#### 1. 饿汉式

饿汉式的实现比较简单，在类加载的时候，instance 静态实例已经创建并初始化好了，所以 instance 实例是线程安全的；

不过，这样实现方式不支持延迟加载，初始化时可能会耗性能；

```java
public class IdGenerator1 {
    private AtomicLong id = new AtomicLong(0);
    private static final IdGenerator1 instance = new IdGenerator1();
    private IdGenerator1() {
    }
    public static IdGenerator1 getInstance() {
        return instance;
    }
    public Long getId(){
        return id.incrementAndGet();
    }
}
```



#### 2. 懒汉试

有饿汉式就有懒汉式；

懒汉式对于饿汉式的优势在于它支持延迟加载，不影响初始化性能；

不过懒汉试缺点也很明显，我们给 getInstance() 加了一个 synchronized 锁，导致这个方法并发度很低，性能非常差；

```java
public class IdGenerator2 {
    private AtomicLong id = new AtomicLong(0);
    private static IdGenerator2 instance;
    private IdGenerator2() {
    }
    public static synchronized IdGenerator2 getInstance() {
        if (instance == null) {
            instance = new IdGenerator2();
        }
        return instance;
    }
    public Long getId() {
        return id.incrementAndGet();
    }
}
```





