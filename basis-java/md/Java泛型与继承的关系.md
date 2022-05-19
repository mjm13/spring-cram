# Java泛型和继承一起使用时的场景

父类与接口

```java
interface GenericityInterface<T> {
    String getString(T t);
}
abstract class GenericityClass<A extends Collection, B> {
    abstract String getString(A a, B b);
}
```

## 保持所有泛型

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

## 使用部分父类/接口泛型

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

