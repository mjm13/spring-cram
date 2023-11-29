package com.meijm.basis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping("/spel")
@Slf4j
@RestController
public class SpelController {

    public static void main(String[] args) throws JsonProcessingException {
        String fieldPath = "*list.*props.aa";
        Map<String, Object> data = new HashMap<>();
        TempSpel temp1 = new TempSpel("temp1");
        TempSpel temp2 = new TempSpel("temp2");
        TempSpel temp3 = new TempSpel("temp3");
        TempSpel temp4 = new TempSpel("temp4");
        TempSpel temp5 = new TempSpel("temp5");
        List list = ImmutableList.of(temp1, temp2, temp3, temp4, temp5);
        data.put("list",list);
        String[] fields = fieldPath.split("\\.");
        List<String> expressions = new ArrayList<>();
        String expressionString = "obj";
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (field.startsWith("*")) {
                field = field.substring(1);
                expressions.add(expressionString + "['" + field+"']");
                expressionString = "obj";
            } else {
                expressionString +=  "['" + field+"']";
            }
        }
        expressions.add(expressionString);
        ExpressionParser parser = new SpelExpressionParser();
        convert(expressions, data, parser, 0);
    }

//    public static void main(String[] args) {
//        Map<String,Object> map = new HashMap<>();
//        StandardEvaluationContext context = new StandardEvaluationContext();
//        context.setVariable("map", map);
//        ExpressionParser parser = new SpelExpressionParser();
//        parser.parseExpression("# map['test']").setValue(context,"aaaaa");
//
//        TempSpel tempSpel = new TempSpel();
//        map.put("tempSpel",tempSpel);
//
//        parser.parseExpression("# map['tempSpel']['temp']").setValue(context,"bbb");
//
////        TempSpel temp1 = new TempSpel();
////        TempSpel temp2 = new TempSpel();
////        TempSpel temp3 = new TempSpel();
////        TempSpel temp4 = new TempSpel();
////        TempSpel temp5 = new TempSpel();
////        map.put("list", ImmutableList.of(temp1,temp2,temp3,temp4,temp5));
////        parser.parseExpression("# map['list'].?[# temp]").setValue(context,"bbb");
//        System.out.println(JSONUtil.toJsonStr(map));
//
//    }

    public static void convert(List<String> expressions, Object obj, ExpressionParser parser, int index) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("obj", obj);
        Object result = parser.parseExpression("# " + expressions.get(index)).getValue(context);
        if (result instanceof Collection) {
            Collection datas = (Collection) result;
            int subIndex = index+1;
            for (Object data : datas) {
                convert(expressions, data, parser, subIndex);
            }
        } else {
            System.out.println("result:" + result);
        }
    }

    @GetMapping(value = "/modifyParam")
    public Map<String, Object> modifyParam(Map<String, Object> map) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("map", map);
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("# map['test']").setValue(context, "aaaaa");

        TempSpel tempSpel = new TempSpel("xxx");
        map.put("tempSpel", tempSpel);

        parser.parseExpression("# map['tempSpel']['temp']").setValue(context, "bbb");

//        TempSpel temp1 = new TempSpel();
//        TempSpel temp2 = new TempSpel();
//        TempSpel temp3 = new TempSpel();
//        TempSpel temp4 = new TempSpel();
//        TempSpel temp5 = new TempSpel();
//        map.put("list", ImmutableList.of(temp1, temp2, temp3, temp4, temp5));
//        parser.parseExpression("# map['list']['temp']").setValue(context, "bbb");

        return map;
    }
}

class TempSpel {
    public TempSpel(String temp) {
        this.temp = temp;
        props = ImmutableList.of(ImmutableMap.of("aa",temp+"aa"),ImmutableMap.of("aa",temp+"bb"),ImmutableMap.of("aa",temp+"cc"));
    }
    
    private List<Map<String,String>> props;

    private String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public List<Map<String, String>> getProps() {
        return props;
    }

    public void setProps(List<Map<String, String>> props) {
        this.props = props;
    }
}
