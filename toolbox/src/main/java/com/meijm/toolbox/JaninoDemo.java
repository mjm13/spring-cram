package com.meijm.toolbox;

import org.codehaus.commons.compiler.IScriptEvaluator;
import org.codehaus.janino.ScriptEvaluator;

public class JaninoDemo {

    public static void main(String[] args) {
        try {
            String content="System.out.println(\"Hello world\");";
            IScriptEvaluator evaluator = new ScriptEvaluator();
            evaluator.cook(content);
            evaluator.evaluate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
