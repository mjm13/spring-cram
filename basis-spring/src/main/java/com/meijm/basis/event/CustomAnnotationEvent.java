package com.meijm.basis.event;

import org.springframework.context.ApplicationEvent;

public class CustomAnnotationEvent extends ApplicationEvent {
    private static final long serialVersionUID = -4180050946434009635L;
    /**
     * 类型
     */
    private String type;

    private Integer mark;
    /**
     * 构造方法
     *
     * @param source 事件源
     * @param type   类型
     */
    public CustomAnnotationEvent(Object source, String type) {
        super(source);
        this.type = type;
    }
    public CustomAnnotationEvent(Object source, String type,Integer mark) {
        super(source);
        this.type = type;
        this.mark = mark;
    }
    /**
     * 获取类型
     *
     * @return 获取类型
     */
    public String getType() {
        return type;
    }
}