# [Beetl](http://ibeetl.com/)作为代码引擎使用

Beetl是模版引擎，可以作为web页面渲染使用，也可以作为单纯的模版使用。

[3.0](https://gitee.com/xiandafu/beetl)源码在gitee上。公司项目中有使用到，这里记录下。

其语法有点类似于JSP+JS的混合体。

## 

## POM

```xml
<dependency>
  <groupId>com.ibeetl</groupId>
  <artifactId>beetl</artifactId>
  <version>${version}</version>
</dependency>
```

## 基础语法

| 字符          | 说明                         |
| ----------- | -------------------------- |
| "<%" / "%>" | 逻辑开始  / 逻辑结束               |
| ${表达式}      | 取值操作，表达式为已绑定的变量，无法在逻辑代码中使用 |
| var 变量名称    | 申明变量，弱类型                   |



# 参考资料

https://www.kancloud.cn/xiandafu/beetl3_guide/2138946 
