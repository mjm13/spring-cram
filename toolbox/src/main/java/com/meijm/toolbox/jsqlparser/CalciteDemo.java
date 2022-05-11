package com.meijm.toolbox.jsqlparser;

import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;
import org.apache.calcite.sql.util.SqlShuttle;
import org.apache.calcite.sql.util.SqlVisitor;

@Slf4j
public class CalciteDemo {
    public static void main(String[] args) throws SqlParseException {
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
        sqlNode.accept( new SqlShuttle(){
            public String visit(SqlNodeList nodeList) {
                log.info("type:{}",literal.getKind());
                return "";
            }
        });
    }
}
