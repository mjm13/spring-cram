package com.meijm.toolbox.beetl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BeetlDemo {
    public static void main(String[] args) throws IOException {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        String templateStr = "<%" +
                "var len = json(_root);" +
                "println('len'+len);" +
                "%>";
        Template template = gt.getTemplate(templateStr);
        Map<String,Object> map = new HashMap<>();
        map.put("a",123);
        map.put("b","ada");
        template.binding("_root",map);
        System.out.println(template.render());
    }
}
