package com.meijm.interview.design.behavior.visitor.element;

import com.meijm.interview.design.behavior.visitor.visitor.Visitor;

public interface Skill {
    void accept(Visitor visitor);
}
