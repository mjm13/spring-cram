package com.meijm.toolbox.python;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JythonPython {
    public static void main(String[] args) {
        String filePath = "D:\\Project\\Self\\spring-cram\\toolbox\\src\\main\\java\\com\\meijm\\toolbox\\python\\my-jython.py";
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(filePath);
        PyFunction pyFunction = interpreter.get("add", PyFunction.class);

        StopWatch sw = new StopWatch();
        sw.start("console调用python");
        Random random = RandomUtil.getRandom(true);
        for (int i = 0; i < 10000; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("x", random.nextInt());
            data.put("y", random.nextInt());
            //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
            PyObject pyobj = pyFunction.__call__(new PyString(JSONUtil.toJsonStr(data)));
            log.info("result:{}",pyobj);
        }
        sw.stop();
        log.info(sw.prettyPrint(TimeUnit.MILLISECONDS));
    }
}
