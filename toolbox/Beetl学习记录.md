# [Beetl](http://ibeetl.com/)使用记录

Beetl是模版引擎，可以作为web页面渲染使用，也可以作为单纯的模版使用。

[3.0](https://gitee.com/xiandafu/beetl)源码在gitee上。公司项目中有使用到，这里记录下。

其语法有点类似于JSP+JS的混合体,目前大部分项目都使用前后端分离,模板引擎已经很少在web项目中作为htlm模板引擎使用,不过beetl也可以单独作为模板引擎使用,类似代码模板,sql模板等.

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

> 其它配置参考默认配置文件,其中都可以自己习惯修改关键字（位于/org/beetl/core/beetl-default.properties)

**逻辑控制**

* 条件判断 :除常规if-else,switch-case外,增加了switch-case增强语法,省略break关键字,多个条件使用,隔开
  
  循环:常规for(exp;exp;exp),while,for-in都支持
  
  * for-in:中额外增加了内置变量 
    - **变量名LP.index** 当前的索引，从1开始
    - **变量名LP.even** 索引是否是偶数
    - **变量名LP.odd** 索引是否是奇数
    - **变量名LP.first** 是否是第一个
    - **变量名LP.last** 是否是最后一个
    - **变量名LP.dataIndex** 索引，从0开始
    - **变量名P.size**集合的长度
  * elsefor:表示未进入for循环的处理
  
  ```java
  
  <%
  var list = [];
  for(item in list){
  
  }elsefor{
    print("未有记录");
  }
  %>
  ```

## 

## 解析字符串模版

```java
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        String templateStr = "${json(_root)}";
        Template template = gt.getTemplate(templateStr);
        Map<String,Object> map = new HashMap<>();
        map.put("a",123);
        map.put("b","ada");
        template.binding("_root",map);
        System.out.println(template.render());
```

**说明:**

* tempStr:字符串模板

* _root:根变量,使用根变量则属性访问可省略前缀

* json():内置函数,可将变量转换为json字符串,可作为代码执行后的输出存储

内置方法在org.beetl.ext.fn包中.存在两种情况

1. 实现Function接口,这类方法参照配置文件中配置简写即可访问,配置文件中以FN.作为前缀的配置

2. 独立的工具类:strutil 字符串工具类,配置文件中以FNP.作为前缀的配置,访问时通过strutil.方法名访问.

**常用内置函数:**

| 函数名称               | 说明           |
| ------------------ | ------------ |
| isEmpty            | 判断是否为空       |
| date(str,fmt)      | 日期格式化        |
| json()             | 对象转换为json字符串 |
| type.new(classUrl) | 创建一个java对象   |

**调用JAVA原生方法 :**

使用在逻辑代码/取值表达式中使用@开始则表示使用java语法

```java
${@com.xxxx.constants.Order.getMaxNum()}
<%
var max = @com.xxxx.constants.Order.MAX_NUM;
%>
```



 

# 参考资料

https://www.kancloud.cn/xiandafu/beetl3_guide/2138946 
