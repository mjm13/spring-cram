持续创作，加速成长！这是我参与「掘金日新计划 · 6 月更文挑战」的第3天，[点击查看活动详情](https://juejin.cn/post/7099702781094674468)

[JSqlParser](https://github.com/JSQLParser/JSqlParser/wiki) 是一个SQL语句解析器，支持常见数据库包含Oracle、SqlServer、MySQL、PostgreSQL，
可以直接操作由语句生成的对象并生成新的sql，也可以使用访问者模式访问sql转换而来的对象。mybatis-plus权限隔离组件就是用的这个工具包来动态修改sql

原始版在：http://jsqlparser.sourceforge.net/上，最后更新时间是2011年，后来Tobias在github上持续维护这个项目
有兴趣的同学建议去官网看看

# 解析sql简介
1. 使用net.sf.jsqlparser.parser.CCJSqlParserUtil将输入转为Statement
2. 使用访问者接口xxxAdapter或类型强转为具体的子类
> 由于官网文档写的非常简洁，源码注释也不是太全面建议逐步调试观察。
> 例如查询语句在此项目中分为WithItem部分，和SqlBody部分，SqlBody下才是具体的sql语句
# 完整示例
示例中数据表为ruoyi项目中的数据表。
```java
        String sqlStr = "SELECT\n" +
                "\tsu.dept_id `deptId`,\n" +
                "\tsu.user_id,\n" +
                "\tsr.role_id,\n" +
                "\tsu.user_name,\n" +
                "\tsd.dept_name,\n" +
                "\tsr.role_name\n" +
                "FROM\n" +
                "\tsys_user AS su\n" +
                "JOIN sys_dept sd ON su.dept_id = sd.dept_id\n" +
                "JOIN sys_user_role sur ON sur.user_id = su.user_id\n" +
                "JOIN sys_role sr ON sur.role_id = sr.role_id\n" +
                "WHERE\n" +
                "\tsd.dept_name = '研发部门'\n" +
                "\tand su.user_name = 'admin'\n" +
                "\tand su.dept_id = 103\n" +
                "\tor sr.role_name = '超级管理员'\n" +
                "ORDER BY\n" +
                "\tsd.create_time DESC";
        Select querySql = (Select)CCJSqlParserUtil.parse(sqlStr);
        querySql.getSelectBody().accept(new SelectVisitorAdapter () {
            @Override
            public void visit(PlainSelect plainSelect) {
                log.info("--------------查询列名----------------------------------------");
               plainSelect.getSelectItems().stream().forEach(selectItem -> {
                   selectItem.accept(new SelectItemVisitorAdapter() {
                       @Override
                       public void visit(SelectExpressionItem selectExpressionItem) {
                           log.info(selectExpressionItem.getExpression().toString());
                           if (selectExpressionItem.getAlias()!=null) {
                               log.info("列别名 {}",selectExpressionItem.getAlias().getName());
                           }
                       }
                   });
               });
                log.info("--------------From Table Info----------------------------------------");
                log.info(plainSelect.getFromItem().toString());
                if (plainSelect.getFromItem().getAlias()!=null) {
                    log.info("表别名"+plainSelect.getFromItem().getAlias().getName());
                }
                log.info("--------------Join Table Info----------------------------------------");
                plainSelect.getJoins().stream().forEach(join -> {
                    log.info(join.toString());
                    log.info("关联表：{} ",join.getRightItem());
                    if (join.getRightItem().getAlias()!=null) {
                        log.info("关联表别名：{}",join.getRightItem().getAlias().getName());
                    }
                    log.info("关联条件：{}",join.getOnExpression().toString());
                });
                log.info("--------------Where  Info----------------------------------------");
                plainSelect.getWhere().accept(new ExpressionVisitorAdapter() {
                    @Override
                    public void visitBinaryExpression(BinaryExpression expr) {
                        log.info("表达式：{}",expr.toString());
                        log.info("表达式左侧：{}",expr.getLeftExpression().toString());
                        log.info("表达式右侧：{}",expr.getRightExpression().toString());
                    }
                });
                log.info("--------------增加查询条件----------------------------------------");
                try {
                    plainSelect.setWhere(new AndExpression(CCJSqlParserUtil.parseCondExpression("1=1"),plainSelect.getWhere()));
                } catch (JSQLParserException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        log.info("语句：{}",querySql.toString());
```







# 参考资料
https://github.com/JSQLParser/JSqlParser/wiki