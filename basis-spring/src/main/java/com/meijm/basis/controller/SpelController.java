package com.meijm.basis.controller;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/spel")
@Slf4j
@RestController
public class SpelController {

    @GetMapping(value = "/modifyParam")
    public Map<String,Object> modifyParam(Map<String,Object> map){
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("map", map);
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("# map['test']").setValue(context,"aaaaa");

        TempSpel tempSpel = new TempSpel();
        map.put("tempSpel",tempSpel);
        
        parser.parseExpression("# map['tempSpel']['temp']").setValue(context,"bbb");

        TempSpel temp1 = new TempSpel();
        TempSpel temp2 = new TempSpel();
        TempSpel temp3 = new TempSpel();
        TempSpel temp4 = new TempSpel();
        TempSpel temp5 = new TempSpel();
        map.put("list", ImmutableList.of(temp1,temp2,temp3,temp4,temp5));
        parser.parseExpression("# map['list']['temp']").setValue(context,"bbb");
        
        return map;
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("map", map);
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("# map['test']").setValue(context,"aaaaa");

        TempSpel tempSpel = new TempSpel();
        map.put("tempSpel",tempSpel);

        parser.parseExpression("# map['tempSpel']['temp']").setValue(context,"bbb");

//        TempSpel temp1 = new TempSpel();
//        TempSpel temp2 = new TempSpel();
//        TempSpel temp3 = new TempSpel();
//        TempSpel temp4 = new TempSpel();
//        TempSpel temp5 = new TempSpel();
//        map.put("list", ImmutableList.of(temp1,temp2,temp3,temp4,temp5));
//        parser.parseExpression("# map['list'].?[# temp]").setValue(context,"bbb");
        System.out.println(JSONUtil.toJsonStr(map));

    }

}

class TempSpel{
    private String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
