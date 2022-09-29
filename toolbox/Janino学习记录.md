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
**表达式执行示例**
```java
import org.codehaus.janino.*;
public class JaninoDemo {

    public static void main(String[] args) throws InvocationTargetException, CompileException {
        ExpressionEvaluator ee = new ExpressionEvaluator();
        ee.setParameters(new String[]{"param"}, new Class[]{Param.class});
        ee.setExpressionType(String.class);
        ee.cook("param.getA() + param.getB()");
        Param param = new Param();
        param.setA("a");
        param.setB("B");
        String resultStr = (String) ee.evaluate(new Param[]{param});
        System.out.println(resultStr);
    }
}
public class Param {
    private String a;
    private String b;
    //省略get/set……
}
```
* ee.setParameters：设置入参类型
* ee.setExpressionType：设置返回值类型
* ee.cook：设置需处理的表达式，支持多种格式String/File/InputStream……
* ee.evaluate：执行表达式并获取结果

> 参数所对应的JavaBean需为public且不支持lombok

**代码段执行示例:**下面的示例为代码调用静态方法
```java

public class JaninoDemo {

    public static void main(String[] args) throws InvocationTargetException, CompileException {
//        demo.expressionEvaluator();
        scriptEvaluator();
    }

    public static void expressionEvaluator() {
        try {
            ExpressionEvaluator ee = new ExpressionEvaluator();
            ee.setParameters(new String[]{"a", "b"}, new Class[]{int.class, int.class});
            ee.setExpressionType(int.class);
            ee.cook("a + b");
            int result = (Integer) ee.evaluate(new Integer[]{8, 9});
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scriptEvaluator() throws CompileException, InvocationTargetException {
        ScriptEvaluator se = new ScriptEvaluator();
        se.cook("import static com.meijm.toolbox.janino.JaninoDemo.*;" +
                "expressionEvaluator();");
        se.setDebuggingInformation(true, true, true);
        se.evaluate(null);
    }

}
```
* se.setDebuggingInformation：设置调试开关，打开后脚本对应的方法支持调试
* 调用的expressionEvaluator() 方法不能抛出异常，需自己处理异常信息