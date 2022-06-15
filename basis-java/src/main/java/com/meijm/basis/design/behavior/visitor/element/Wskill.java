package com.meijm.basis.design.behavior.visitor.element;

import com.meijm.basis.design.behavior.visitor.visitor.Visitor;
import lombok.Data;

@Data
public class Wskill implements Skill {
    private String key="快捷键W";
    private String index="2";

    @Override
    public void accept(Visitor visitor) {
        visitor.visitWskill(this);
    }
}
