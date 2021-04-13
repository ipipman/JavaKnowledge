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



#### 3. 双重检测

饿汉式不支持延迟加载，懒汉式有性能问题，不支持高并发；

**双重检测** 兼顾了性能和延迟加载问题；

在实现方式中，只要 instance 被创建后，即便再调用 getInstance() 函数也不会再进入到加锁逻辑中了；

```java
public class IdGenerator3 {
    private AtomicLong id = new AtomicLong(0);
    private static volatile IdGenerator3 instance;
    private IdGenerator3() {
    }
    public static IdGenerator3 getInstance() {
        if (instance == null) {
            synchronized (IdGenerator3.class) {
                if (instance == null) {
                    instance = new IdGenerator3();
                }
            }
        }
        return instance;
    }
    public Long getId() {
        return id.incrementAndGet();
    }
}
```



#### 4. 静态内部类

**静态内部类** 它有点类似于饿汉模式，但又能做到延迟加载；

SingletonHolder 是一个内部静态类，当外部类 IdGenerator4 被加载的时候，并不会创建  SingletonHodler 实例对象；

只有在 getInstance() 方式时，SingletonHodler 类才会被加载；

instance 的唯一性、创建过程的线程安全由 JVM 来保证；

```java
public class IdGenerator4 {
    private AtomicLong id = new AtomicLong(0);
    private IdGenerator4() {
    }
    private static class SingletonHolder {
        public final static IdGenerator4 instance = new IdGenerator4();
    }
    public static IdGenerator4 getInstance() {
        return SingletonHolder.instance;
    }
    public Long getId() {
        return id.incrementAndGet();
    }
}
```





