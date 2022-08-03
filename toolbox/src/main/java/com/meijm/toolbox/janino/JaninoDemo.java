package com.meijm.toolbox.janino;

import jdk.nashorn.internal.runtime.Context;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;


import java.lang.reflect.InvocationTargetException;
import java.security.Permissions;

public class JaninoDemo {

    public static void main(String[] args) throws InvocationTargetException, CompileException {
//        demo.expressionEvaluator();
        scriptEvaluator();

    }

    /**
     * 表达式
     * @throws CompileException
     * @throws InvocationTargetException
     */
    public static void expressionEvaluator() {
        // Now here's where the story begins...
        try {
            ExpressionEvaluator ee = new ExpressionEvaluator();

            // The expression will have two "int" parameters: "a" and "b".
            ee.setParameters(new String[]{"a", "b"}, new Class[]{int.class, int.class});

            // And the expression (i.e. "result") type is also "int".
            ee.setExpressionType(int.class);

            // And now we "cook" (scan, parse, compile and load) the fabulous expression.
            ee.cook("a + b");

            // Eventually we evaluate the expression - and that goes super-fast.
            int result = (Integer) ee.evaluate(new Integer[]{8, 9});
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scriptEvaluator() throws CompileException, InvocationTargetException {
        ScriptEvaluator se = new ScriptEvaluator();

//        se.cook(
//                "int a=1;" +
//                        "int b=2;" +
//                        "System.out.println(a+b);"
//        );
        se.cook("import static com.meijm.toolbox.janino.JaninoDemo.*;" +
                "expressionEvaluator();");
        se.setDebuggingInformation(true,true,true);
        se.evaluate(null);
    }
}
