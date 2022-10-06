# [Maven官网](https://maven.apache.org/guides/index.html)

Maven介绍

# 模块的继承和聚合

模块之间可以通过在子模块中增加parent标签来指定父模块,父模块中通过modules来指定该父模块聚合的子模块。

``` xml
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <artifactId>mavenDemo</artifactId>
        <groupId>org.meijm</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>firstLevel</artifactId>
    <packaging>pom</packaging>

    <modules>
        <module>secondLevel</module>
    </modules>
```

* modelVersion:固定值 4.0.0，指定当前XML结构
* parent：指定当前模块的父模块，继承父模块的依赖。
* modules：指定当前模块聚合的模块，用于统一构建。
* packaging：当前模块打包类型
    * jar：产出jar包，默认值
    * pom：聚合项目，不进行打包，用于管理模块，通常用于父模块。
    * war：产出war包，老的项目中会使用到。打包完成后放到tomcat等容器中运行。

> 通常会将继承与聚合合并使用，parent中可以增加relativePath标签指定从何处加载父模块pom文件，``` <relativePath/> ```
> 空标签表示先从本地仓库获取

# 父子模块依赖管理
* dependencies：标签下的依赖都会被子标签继承。
* dependencyManagement：标签下的依赖子类不会直接继承，子模块使用时只需要指定groupId，artifactId，用于统一版本。
```xml
<dependencies>
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>5.4.5</version>
    </dependency>
</dependencies>

<dependencyManagement>
  <dependencies>
      <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-dependencies</artifactId>
          <version>${spring-cloud.version}</version>
          <type>pom</type>
          <scope>import</scope>
      </dependency>
      <dependency>
          <groupId>com.alibaba.cloud</groupId>
          <artifactId>spring-cloud-alibaba-dependencies</artifactId>
          <version>${spring-cloud-alibaba.version}</version>
          <type>pom</type>
          <scope>import</scope>
      </dependency>
  </dependencies>
</dependencyManagement>
```
测试项目中每个子项目都会使用到工具包，但是spring-cloud打算用两个不同的体系来进行测试。
所以使用dependencyManagement来管理其它的依赖，具体项目中要使用时才指定。

# dependency标签 
dependency中除了groupId，artifactId，version外还有其它标签可用
* exclusions：排除依赖jar包中的依赖，比如切换log依赖，或者排除依赖包中的低版本依赖。
* optional：值为false/true,默认为false表示子模块继承该依赖，设置为true表示子模块不继承该依赖
* scope：表示jar包在什么情况下使用，有以下几类
  * compile：默认值，表示引入依赖参与全流程。
  * provided：参与编译和测试环节，通常是容器包含的依赖使用此模式，不会打入产出的jar包中。
  * test：参与测试环节，例如junit，testng。
  * import：在dependencyManagement标签下使用，比如spring-cloud。
  * runtime：参与运行环节，会打入jar包中。
  * system：表示不从仓库获取依赖，配合systemPath使用，从本地路径中获取jar包。

> 下面是官方提供的system示例
```xml
<dependencies>
    <dependency>
      <groupId>javax.sql</groupId>
      <artifactId>jdbc-stdext</artifactId>
      <version>2.0</version>
      <scope>system</scope>
      <systemPath>${java.home}/lib/rt.jar</systemPath>
    </dependency>
  </dependencies>
```
# 参考文档

https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html