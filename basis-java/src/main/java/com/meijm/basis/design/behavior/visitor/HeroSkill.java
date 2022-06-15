package com.meijm.basis.design.behavior.visitor;

import com.meijm.basis.design.behavior.visitor.element.*;
import com.meijm.basis.design.behavior.visitor.visitor.Visitor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HeroSkill {
    private List<Skill> skills = new ArrayList<>();
    public HeroSkill(){
        skills.add(new Qskill());
        skills.add(new Wskill());
        skills.add(new Eskill());
        skills.add(new Rskill());
    }

    public void acceptVisitor(Visitor visitor){
        log.info("{}-{}",visitor.getName(),visitor.getTips());

        for (Skill skill : skills) {
            skill.accept(visitor);
        }
    }
}
