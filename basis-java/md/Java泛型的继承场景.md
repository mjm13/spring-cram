# Java泛型的继承场景

**泛型作用：**
1. 编译前检查
2. 运行过程中自动转换类型

## 父类与接口
```java
interface GenericityInterface<T> {
    String getString(T t);
}
abstract class GenericityClass<A extends Collection, B> {
    abstract String getString(A a, B b);
}
```

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
```

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

## 其它-获取运行时类型
```java
class Test<T>{
    public Class getActualClass(){
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }
}
```
