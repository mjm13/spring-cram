package com.meijm.interview.design.behavior.visitor.element;

import com.meijm.interview.design.behavior.visitor.visitor.Visitor;
import lombok.Data;

@Data
public class Rskill implements Skill {
    private String key="快捷键R";
    private String index="4";

    @Override
    public void accept(Visitor visitor) {
        visitor.visitRskill(this);
    }
}
