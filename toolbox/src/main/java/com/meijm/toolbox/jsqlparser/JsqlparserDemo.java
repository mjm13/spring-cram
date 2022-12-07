package com.meijm.toolbox.jsqlparser;

import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang3.RegExUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description 使用net.sf.jsqlparser解析sql语句
 * @Author MeiJM
 * @Date 2022/5/20
 **/
@Slf4j
public class JsqlparserDemo {
    public static void main(String[] args) throws JSQLParserException {
        selectDemo();
    }

    public static void updateDemo() throws JSQLParserException {
        String sqlStr = "UPDATE `eds`.`eds_company` SET `id`='1', `company_no`='1', `company_name`='xx科技', `type`='1', `contact`=NULL, `phone`=NULL, `address`=NULL, `email`=NULL, `description`=NULL,  `creator`=NULL, `creator_name`=NULL, `create_time`=NULL, `modifier`=NULL, `modifier_name`=NULL, `modify_time`=NULL WHERE (`id`='1');\n";
        Statement sql = CCJSqlParserUtil.parse(sqlStr);
        log.info("sql:{}", sqlStr);
        sql.accept(new StatementVisitorAdapter() {
            @Override
            public void visit(Delete delete) {
            }

            @Override
            public void visit(Update update) {
                String tableName = update.getTable().toString();
                try {
                    if (update.getWhere()!=null) {
                        update.setWhere(new AndExpression(CCJSqlParserUtil.parseCondExpression(tableName+".enterprise_id=1"),update.getWhere()));
                    }else{
                        update.setWhere(CCJSqlParserUtil.parseCondExpression(tableName+".enterprise_id=1"));
                    }
                } catch (JSQLParserException e) {
                    throw new RuntimeException(e);
                }
                log.info("new sql:{}", update);
            }
        });
    }

    public static void selectDemo() throws JSQLParserException {
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
                "WHERE  %@cc,dd@%  %@aaa,bbb,and@%\n" +
                "\tsd.dept_name = '研发部门'\n" +
                "\tand su.user_name = 'admin'\n" +
                "\tand su.dept_id = 103\n" +
                "\tor sr.role_name = '超级管理员'\n" +
                "ORDER BY\n" +
                "\tsd.create_time DESC";

        String regex = "%@(.*?)@%";
        List<String> temps =  ReUtil.findAll(regex,sqlStr,0);
        temps.stream().forEach(s -> {
            System.out.println("1111");
            System.out.println(s);
        });
        if (true){
            return;
        }
        Select querySql = (Select) CCJSqlParserUtil.parse(sqlStr);
        querySql.getSelectBody().accept(new SelectVisitorAdapter() {
            @Override
            public void visit(PlainSelect plainSelect) {
                log.info("--------------查询列名----------------------------------------");
                plainSelect.getSelectItems().stream().forEach(selectItem -> {
                    selectItem.accept(new SelectItemVisitorAdapter() {
                        @Override
                        public void visit(SelectExpressionItem selectExpressionItem) {
                            log.info(selectExpressionItem.getExpression().toString());
                            SimpleNode simpleNode = selectExpressionItem.getExpression().getASTNode();
                            if (selectExpressionItem.getAlias() != null) {
                                log.info("列别名 {}", selectExpressionItem.getAlias().getName());
                            }
                        }
                    });
                });
                log.info("--------------From Table Info----------------------------------------");
                log.info(plainSelect.getFromItem().toString());
                if (plainSelect.getFromItem().getAlias() != null) {
                    log.info("表别名" + plainSelect.getFromItem().getAlias().getName());
                }
                log.info("--------------Join Table Info----------------------------------------");
                plainSelect.getJoins().stream().forEach(join -> {
                    log.info(join.toString());
                    log.info("关联表：{} ", join.getRightItem());
                    if (join.getRightItem().getAlias() != null) {
                        log.info("关联表别名：{}", join.getRightItem().getAlias().getName());
                    }
                    log.info("关联条件：{}", join.getOnExpression().toString());
                });
                log.info("--------------Where  Info----------------------------------------");
                plainSelect.getWhere().accept(new ExpressionVisitorAdapter() {
                    @Override
                    public void visitBinaryExpression(BinaryExpression expr) {
                        log.info("表达式：{}", expr.toString());
                        log.info("表达式左侧：{}", expr.getLeftExpression().toString());
                        log.info("表达式右侧：{}", expr.getRightExpression().toString());
                    }
                });
                log.info("--------------增加查询条件----------------------------------------");
                try {
                    plainSelect.setWhere(new AndExpression(CCJSqlParserUtil.parseCondExpression("1=1"), plainSelect.getWhere()));
                } catch (JSQLParserException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        log.info("语句：{}", querySql);
    }

}
