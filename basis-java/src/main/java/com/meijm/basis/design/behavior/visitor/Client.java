package com.meijm.basis.design.behavior.visitor;

import com.meijm.basis.design.behavior.visitor.visitor.Ashe;
import com.meijm.basis.design.behavior.visitor.visitor.Darius;
import com.meijm.basis.design.behavior.visitor.visitor.Visitor;

public class Client {
    public static void main(String[] args) {
        HeroSkill heroSkill = new HeroSkill();
        Visitor ashe = new Ashe();
        heroSkill.acceptVisitor(ashe);
        Visitor darius = new Darius();
        heroSkill.acceptVisitor(darius);
    }
}
