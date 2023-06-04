package com.meijm.actuator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GCtestController {

    /**
     * 刷内存，执行之后访问
     * http://localhost:8080/actuator/metrics/jvm.gc.pause?tag=action:end%20of%20major%20GC
     * 服务启动内存-Xms100m -Xmx200m
     * 查看fullgc次数及耗时
     * 命令行验证  jstat -gc 进程id 1000
     * @return
     */
    @GetMapping("/")
    public String index(){
        new Thread(() -> {
            int i = 0;
            List<String> arr = new ArrayList<>();
            do{
                i++;
//                System.out.println(i);
                arr.add("    RestMessage<Boolean> notice(@RequestBody List<Map<String, Object>> noticeMessageList) {\n" +
                        "        //1、Map转换NoticeMessage\n" +
                        "        List<NoticeMessage> messageList = Lists.newArrayList();\n" +
                        "        for (Map<String, Object> objectMap : noticeMessageList) {\n" +
                        "            NoticeMessage noticeMessage = new NoticeMessage();\n" +
                        "            noticeMessage.setIndicatorType((String) objectMap.get(INDICATOR_TYPE));\n" +
                        "            noticeMessage.setIndicators((Map<String, Object>) objectMap.get(INDICATORS));\n" +
                        "            noticeMessage.setExtData((Map<String, Object>) objectMap.get(EXT_DATA));\n" +
                        "            messageList.add(noticeMessage);\n" +
                        "        }\n" +
                        "        //2、不存在的枚举动态生成-建立服务名称和指标类别关系映射\n" +
                        "        String indicatorType = messageList.get(0).getIndicatorType();\n" +
                        "        IndicatorTypeEnum indicatorTypeEnum = IndicatorTypeEnum.findEnumByValue(indicatorType);\n" +
                        "        if (Objects.isNull(indicatorTypeEnum)) {\n" +
                        "            NoticeDynamicEnumUtils.addEnum(\n" +
                        "                    IndicatorTypeEnum.class, indicatorType,\n" +
                        "                    new Class[]{String.class, String.class},\n" +
                        "                    new Object[]{indicatorType, indicatorType});\n" +
                        "            indicatorTypeEnum = IndicatorTypeEnum.findEnumByValue(indicatorType);\n" +
                        "        }\n" +
                        "        //3、通知调用\n" +
                        "        noticeCommonService.collectNoticeInfo(indicatorTypeEnum, messageList);\n" +
                        "        return RestMessage.doSuccess(Boolean.TRUE);\n" +
                        "    }\n" +
                        "\t\n" +
                        "\t\n" +
                        "\t\n" +
                        "\t    private String service;\n" +
                        "    private String sql;\n" +
                        "    private String consoleSql;\n" +
                        "    private String sqlId;\n" +
                        "    private Date startTime;\n" +
                        "    private long period;\n" +
                        "\t\n" +
                        "\t\n" +
                        "\t\n" +
                        "\t\n" +
                        "\thttp://localhost:8080/actuator/metrics/jvm.gc.pause?tag=action:end of major GC"+i);
                if(i>30000){
                    arr.clear();
                    i = 0;
                }
            }while (true);
        }).start();
        return "index";
    }
}
