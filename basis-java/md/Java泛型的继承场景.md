持续创作，加速成长！这是我参与「掘金日新计划 · 6 月更文挑战」的第1天，[点击查看活动详情](https://juejin.cn/post/7099702781094674468)

**泛型用法**
1. 泛型类/接口：在类/接口后增加关键字"<泛型标识>"，申明后该类/接口的具体方法可以使用此标识作为，入参/出参的类型。
2. 泛型方法：在方法访问修饰符后增加关键字"<泛型标识>"。

> 泛型标识：可以是任意字符串，通常使用单个大写字母表示.
> 可以有多个泛型标识代表不同参数

**泛型作用：**
1. 编译前编译工具检查
2. 运行过程中自动转换类型

下面主要说明泛型在继承场景下的使用。
## 父类与接口
```java
interface GenericityInterface<T> {
    String getString(T t);
}
abstract class GenericityClass<A extends Collection, B> {
    abstract String getString(A a, B b);
}
```
> 子类/实现类可使用@Override让编译工具检查是否重写了父类中方法，否则可能是重载了父类方法，
> 导致在使用父类/接口封装业务时出现偏差。


## 沿用所有泛型
```java
class SubGenericity1<T, B, A extends Collection> extends GenericityClass<A, B> implements GenericityInterface<T> {
    @Override
    public String getString(T o) {
        return o.getClass().toString();
    }

    @Override
    String getString(A a, B o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}
```
* GenericityInterface/GenericityClass 中泛型标识符可以使用任意字符，但个数不能大于实际个数。
* SubGenericity1<T, B, A extends Collection>，表示当前类使用三个泛型标识符,T,B,A，标识需要和继承的类/接口保持一致
  当父类泛型存在限制时子类的泛型需要小于或等于父类中泛型范围，例如父类范围是Collection，子类可以是List，但反过来不行。

## 合并泛型
```java
class SubGenericity2<T extends Collection, B> extends GenericityClass<T, B> implements GenericityInterface<T> {
    @Override
    public String getString(T o) {
        return o.getClass().toString();
    }

    @Override
    String getString(T a, B o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}

class SubGenericity21<T extends Collection> extends GenericityClass<T, T> implements GenericityInterface<T> {
    @Override
    public String getString(T o) {
        return o.getClass().toString();
    }

    @Override
    String getString(T a, T o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}
```
* GenericityClass<T, T>：从上述代码可知泛型申明类似与占位符，只要与父类个数相同即可，标识符一致表示是同一个泛型约束

## 不使用泛型
```java
class SubGenericity3 extends GenericityClass implements GenericityInterface {
    @Override
    public String getString(Object o) {
        return o.getClass().toString();
    }

    @Override
    String getString(Collection collection, Object o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}
```
* 继承时不写泛型则表示不继承父类/接口中泛型约束，覆盖方法时使用Object代替了泛型约束，类型擦除的一种体现

## 部分使用泛型

```java
class SubGenericity4<T> extends GenericityClass implements GenericityInterface<T> {
    @Override
    public String getString(T o) {
        return o.getClass().toString();
    }

    @Override
    String getString(Collection collection, Object o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}
class SubGenericity5<T extends Collection,B> extends GenericityClass<T,B> implements GenericityInterface {
    @Override
    public String getString(Object o) {
        return o.getClass().toString();
    }

    @Override
    String getString(T collection, B o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}
```

## 继承时指定类型
```java
class SubGenericity6 extends GenericityClass<List,String> implements GenericityInterface<Map> {
    @Override
    public String getString(Map o) {
        return o.getClass().toString();
    }

    @Override
    String getString(List collection,String o) {
        return o.getClass().toString() + "|" + o.getClass();
    }
}
```
* 通常使用此模式使用泛型

## 其它-获取运行时类型
```java
class Test<T>{
    public Class getActualClass(){
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }
}
```
> 在子类需要沿用泛型，且需要创建泛型对应对象时会使用此方法，
> 其中type.getActualTypeArguments()返回的是该类申明的所有泛型，可能存在多个。

# 参考资料
https://docs.oracle.com/javase/tutorial/java/generics/types.html