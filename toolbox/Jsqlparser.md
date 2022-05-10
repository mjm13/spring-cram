# 使用JSqlParser 解析sql
# [JSqlParser 简介](https://github.com/JSQLParser/JSqlParser/wiki)

JSqlParser是一个SQL语句解析器，支持常见数据库包含Oracle、SqlServer、MySQL、PostgreSQL，可以直接操作由语句生成的对象并生成新的sql，也可以使用访问者模式访问sql转换而来的对象

# 使用

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