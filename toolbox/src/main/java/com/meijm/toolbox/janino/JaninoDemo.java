package com.meijm.toolbox.janino;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;

import java.lang.reflect.InvocationTargetException;

public class JaninoDemo {

    public static void main(String[] args) throws InvocationTargetException, CompileException {
//        demo.expressionEvaluator();
        scriptEvaluator();
    }

    public static void expressionEvaluator() {
        try {
            ExpressionEvaluator ee = new ExpressionEvaluator();
            ee.setParameters(new String[]{"a", "b"}, new Class[]{int.class, int.class});
            ee.setExpressionType(int.class);
            ee.cook("a + b");
            int result = (Integer) ee.evaluate(new Integer[]{8, 9});
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scriptEvaluator() throws CompileException, InvocationTargetException {
        ScriptEvaluator se = new ScriptEvaluator();
        se.cook("import static com.meijm.toolbox.janino.JaninoDemo.*;" +
                "expressionEvaluator();");
        se.setDebuggingInformation(true, true, true);
        se.evaluate(null);
    }

}

