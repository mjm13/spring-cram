package com.meijm.interview.design.behavior.visitor.element;

import com.meijm.interview.design.behavior.visitor.visitor.Visitor;
import lombok.Data;

@Data
public class Qskill implements Skill {
    private String key="快捷键Q";
    private String index="1";

    @Override
    public void accept(Visitor visitor) {
        visitor.visitQskill(this);
    }
}
