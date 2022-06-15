package com.meijm.basis.design.behavior.visitor.element;

import com.meijm.basis.design.behavior.visitor.visitor.Visitor;
import lombok.Data;

@Data
public class Eskill implements Skill {
    private String key="快捷键E";
    private String index="3";

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEskill(this);
    }
}
