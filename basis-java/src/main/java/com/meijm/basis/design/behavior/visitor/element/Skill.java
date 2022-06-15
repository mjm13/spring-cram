package com.meijm.basis.design.behavior.visitor.element;

import com.meijm.basis.design.behavior.visitor.visitor.Visitor;

public interface Skill {
    void accept(Visitor visitor);
}
