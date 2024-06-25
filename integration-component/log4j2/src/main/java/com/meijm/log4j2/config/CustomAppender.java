package com.meijm.log4j2.config;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;


@Plugin(name = "Custom", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class CustomAppender extends AbstractAppender {

    protected CustomAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout, true);
    }

    @PluginFactory
    public static CustomAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginAttribute("ignoreExceptions") boolean ignore,
            Filter filter,
            Layout<? extends Serializable> layout) {
        return new CustomAppender(name, filter, layout);
    }

    @Override
    public void append(LogEvent event) {
        // 实现日志处理逻辑，例如发送日志到远程服务器
        Throwable throwable = event.getMessage().getThrowable();
        if (event.getLevel().intLevel() <= Level.ERROR.intLevel() && throwable != null) {
            System.out.println(SpringUtil.getProperty("server.port"));
            System.out.println("Log event received: " + event.getMessage().getFormattedMessage());
        }
    }
}