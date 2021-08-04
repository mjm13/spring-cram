package com.meijm.interview.design.behavior.visitor.visitor;

import com.meijm.interview.design.behavior.visitor.element.Eskill;
import com.meijm.interview.design.behavior.visitor.element.Qskill;
import com.meijm.interview.design.behavior.visitor.element.Rskill;
import com.meijm.interview.design.behavior.visitor.element.Wskill;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Ashe implements Visitor {
    public String name = "艾希";
    public String tips = "一个部族，一个民族，一个弗雷尔卓德。";
    @Override
    public void visitQskill(Qskill qskill) {
        log.info("技能名称：{}---按键：{}-技能位置：{}，","射手的专注",qskill.getKey(),qskill.getIndex());
    }

    @Override
    public void visitWskill(Wskill wskill) {
        log.info("技能名称：{}---按键：{}-技能位置：{}，","万箭齐发",wskill.getKey(),wskill.getIndex());

    }

    @Override
    public void visitEskill(Eskill eskill) {
        log.info("技能名称：{}---按键：{}-技能位置：{}，","鹰击长空",eskill.getKey(),eskill.getIndex());
    }

    @Override
    public void visitRskill(Rskill rskill) {
        log.info("技能名称：{}---按键：{}-技能位置：{}，","魔法水晶箭",rskill.getKey(),rskill.getIndex());
    }
}
