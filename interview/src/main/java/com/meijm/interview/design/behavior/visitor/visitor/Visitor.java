package com.meijm.interview.design.behavior.visitor.visitor;

import com.meijm.interview.design.behavior.visitor.element.Eskill;
import com.meijm.interview.design.behavior.visitor.element.Qskill;
import com.meijm.interview.design.behavior.visitor.element.Rskill;
import com.meijm.interview.design.behavior.visitor.element.Wskill;

public interface Visitor {
     void visitQskill(Qskill qskill);
     void visitWskill(Wskill wskill);
     void visitEskill(Eskill eskill);
     void visitRskill(Rskill rskill);

     String getName();
     String getTips();
}
