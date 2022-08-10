# [Janino](https://janino-compiler.github.io/janino/)
Janino是一个轻量级的Java编译器。作为Library，它可以直接在Java程序中调用，动态编译java代码并加载。
编译时可以直接引用JVM中已经加载的类.
支持绝大部分java语法,官方网站上有详细各个版本支持的语法.


## 依赖
```xml
        <dependency>
            <groupId>org.codehaus.janino</groupId>
            <artifactId>janino</artifactId>
            <version>3.0.7</version>
        </dependency>
```

## 基础用法
```java
import org.codehaus.janino.*;
 
ExpressionEvaluator ee = new ExpressionEvaluator();
ee.cook("3 + 4");
System.out.println(ee.evaluate(null)); // Prints "7".
```

