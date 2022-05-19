# 使用Apcahe Calcite 解析sql
# [Apcahe Calcite 简介](https://calcite.apache.org/)

Apcahe Calcite是一个基于SQL语法的查询引擎，可以使用sql语句查询多种数据源，例如csv，mongodb，redis等，

**主要功能**
● 标准SQL解析和校验：行业标准的 SQL 解析器、验证器和 JDBC 驱动程序。
● 查询优化：在关系代数中表示查询，使用计划规则进行转换，并根据成本模型进行优化。
● 任何数据，任务地方：连接到第三方数据源，浏览元数据，并通过将计算推送到数据进行优化。

> 下面的代码仅仅是解析sql部分的demo 

# sql解析的使用

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
        SqlNode sqlNode = SqlParser.create(sqlStr, SqlParser.config().withLex(Lex.MYSQL)).parseQuery();
        sqlNode.accept(new SqlBasicVisitor<String>() {
            public String visit(SqlCall call) {
                if (call.getKind().equals(SqlKind.SELECT)) {
                    SqlSelect select = (SqlSelect) call;
                    log.info("--------------查询列名----------------------------------------");
                    select.getSelectList().forEach(colum -> {
                        if (SqlKind.AS.equals(colum.getKind())) {
                            SqlBasicCall basicCall = (SqlBasicCall) colum;
                            log.info("{} as {}", basicCall.getOperandList().get(0).toString(), basicCall.getOperandList().get(1).toString());
                        } else if (SqlKind.IDENTIFIER.equals(colum.getKind())) {
                            log.info(colum.toString());
                        }
                    });
                    log.info("--------------From Table Info----------------------------------------");
                    select.getFrom().accept(new SqlBasicVisitor<String>() {
                        public String visit(SqlCall call) {
                            if (call.getKind().equals(SqlKind.JOIN)) {
                                SqlJoin join = (SqlJoin) call;
                                log.info("join.getRight:{},join.getCondition{}", join.getRight().toString(), join.getCondition().toString());
                                if (!join.getLeft().getKind().equals(SqlKind.JOIN)) {
                                    log.info("join.getLeft:{}", join.getLeft().toString());
                                }
                            }
                            return call.getOperator().acceptCall(this, call);
                        }
                    });
                    log.info("--------------Where  Info-----------------------------------------");
                    select.getWhere().accept(new SqlBasicVisitor<String>() {
                        public String visit(SqlCall call) {
                            if (call.getKind().equals(SqlKind.AND) || call.getKind().equals(SqlKind.OR)) {
                                SqlBasicCall sql = (SqlBasicCall) call;
                                SqlBasicCall left = (SqlBasicCall) sql.getOperandList().get(0);
                                SqlBasicCall right = (SqlBasicCall) sql.getOperandList().get(1);
                                log.info("kind:{},right:{}", sql.getKind(), right);
                                if (!left.getKind().equals(SqlKind.AND) && !left.getKind().equals(SqlKind.OR)) {
                                    log.info("left:{}", left);
                                }
                            }
                            return call.getOperator().acceptCall(this, call);
                        }
                    });
                    log.info("--------------增加查询条件----------------------------------------");
                    try {
                        SqlNode condition = SqlParser.create("1=1").parseExpression();
                        SqlNode where = SqlUtil.andExpressions(select.getWhere(),condition);
                        select.setWhere(where);
                    } catch (SqlParseException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("语句:{}", select);
                }
                return call.getOperator().acceptCall(this, call);
            }
        });
```


# 参考资料
https://calcite.apache.org/docs/tutorial.html