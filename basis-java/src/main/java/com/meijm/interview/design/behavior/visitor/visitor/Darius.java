package com.meijm.interview.design.behavior.visitor.visitor;

import com.meijm.interview.design.behavior.visitor.element.Eskill;
import com.meijm.interview.design.behavior.visitor.element.Qskill;
import com.meijm.interview.design.behavior.visitor.element.Rskill;
import com.meijm.interview.design.behavior.visitor.element.Wskill;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Darius implements Visitor {
    public String name = "德莱厄斯";
    public String tips = "历史只记得胜利者。顺应诺克萨斯，你将名垂千古。";
    @Override
    public void visitQskill(Qskill qskill) {
        log.info("技能名称：{}---按键：{}-技能位置：{}，","大杀四方",qskill.getKey(),qskill.getIndex());
    }

    @Override
    public void visitWskill(Wskill wskill) {
        log.info("技能名称：{}---按键：{}-技能位置：{}，","致残打击",wskill.getKey(),wskill.getIndex());
    }

    @Override
    public void visitEskill(Eskill eskill) {
        log.info("技能名称：{}---按键：{}-技能位置：{}，","无情铁手",eskill.getKey(),eskill.getIndex());
    }

    @Override
    public void visitRskill(Rskill rskill) {
        log.info("技能名称：{}---按键：{}-技能位置：{}，","诺克萨斯断头台",rskill.getKey(),rskill.getIndex());
    }
}
