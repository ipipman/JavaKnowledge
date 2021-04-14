### Proxy 设计模式

#### 什么是代理模式?

**代理模式** （Proxy Design Pattern）；

它在不改变原始类（或叫被代理类）代码的情况下，通过引入代理类给原始类附加功能；



#### Java1.3 动态代理InvocationHandler

```java
// Java 1.3 动态代理
public class InvocationProxy implements InvocationHandler {

    private Object delegate;

    public Object bind(Object delegate) {
        this.delegate = delegate;
        ClassLoader classLoader = delegate.getClass().getClassLoader();
        Class<?>[] clazz = delegate.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, clazz, this::invoke);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            System.out.println("InvocationHandler start ...");
            result = method.invoke(delegate, args);
            System.out.println("InvocationHandler end ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // TODO Testing ...
    public static void main(String[] args) {
        InvocationProxy invocationProxy = new InvocationProxy();
        IPersonService personService = (IPersonService) invocationProxy.bind(new PersonServiceImpl());
        personService.doing();
    }
}
```

