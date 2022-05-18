package com.meijm.toolbox.jsqlparser;

import ch.qos.logback.core.db.dialect.MySQLDialect;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.dialect.MssqlSqlDialect;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;

@Slf4j
public class CalciteDemo {
    public static void main(String[] args) throws Exception {
        selectDemo();
    }
    public static void selectDemo() throws SqlParseException {
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
        SqlDialect dialect = MysqlSqlDialect.DEFAULT;
        SqlNode sqlNode = SqlParser.create(sqlStr, SqlParser.config().withLex(Lex.MYSQL)).parseQuery();
        sqlNode.accept(new SqlBasicVisitor<String>(){
            public String visit(SqlCall call) {
                if (call.getKind().equals(SqlKind.SELECT)) {
                    SqlSelect select = (SqlSelect) call;
                    log.info("--------------查询列名----------------------------------------");
                    select.getSelectList().forEach(colum ->{
                        if (SqlKind.AS.equals(colum.getKind())) {
                            SqlBasicCall basicCall =  (SqlBasicCall)colum;
                            basicCall.getOperandList().get(0).toString();
                        }else if (SqlKind.IDENTIFIER.equals(colum.getKind())){
                            log.info(colum.toString());
                        }
                    } );
                    log.info("--------------From Table Info----------------------------------------");
                    select.getFrom().accept(new SqlBasicVisitor<String>(){

                    });
                    log.info("from:{}",select.getFrom().toSqlString(dialect).getSql());
                    log.info("--------------Where  Info-----------------------------------------");
                    log.info(select.getWhere().toString());
                    log.info("--------------增加查询条件----------------------------------------");
//                    SqlCall condition  = SqlStdOperatorTable.AND.createCall(select.getWhere().getParserPosition(),);
//                    select.setWhere(condition);
//                    log.info("语句:{}",select.toString());
                }

                return call.getOperator().acceptCall(this, call);
            }
        });
    }
}
