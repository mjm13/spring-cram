package com.meijm.basis.controller;

import cn.hutool.json.JSONUtil;
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
        return map;
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
