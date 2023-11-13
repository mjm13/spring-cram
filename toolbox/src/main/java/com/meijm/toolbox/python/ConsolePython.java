package com.meijm.toolbox.python;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConsolePython {
//    耗时
//---------------------------------------------
//    s         %     Task name
//---------------------------------------------
//    000000494  100%   console调用python
    
    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        sw.start("console调用python");
        Random random = RandomUtil.getRandom(true);
        for (int i = 0; i < 10000; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("x", random.nextInt());
            data.put("y", random.nextInt());
            String pythonPath = "D:\\Project\\Self\\spring-cram\\toolbox\\src\\main\\java\\com\\meijm\\toolbox\\python\\my.py";
            String[] arguments = new String[]{"python", pythonPath, JSONUtil.toJsonStr(data).replace("\"", "'")};//指定命令、路径、传递的参数
            StringBuilder sbrs = null;
            StringBuilder sberror = null;
            try {
                ProcessBuilder builder = new ProcessBuilder(arguments);
                Process process = builder.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));//获取字符输入流对象
                BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(), "utf-8"));//获取错误信息的字符输入流对象
                String line = null;
                sbrs = new StringBuilder();
                sberror = new StringBuilder();
                //记录输出结果
                while ((line = in.readLine()) != null) {
                    sbrs.append(line);
                }
                //记录错误信息
                while ((line = error.readLine()) != null) {
                    sberror.append(line);
                }
                in.close();
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            log.info("输出结果:{}", sbrs.toString());
        }
        sw.stop();
        log.info(sw.prettyPrint(TimeUnit.SECONDS));
    }
}
