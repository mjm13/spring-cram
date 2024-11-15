package com.meijm.basis.startup;

import com.meijm.basis.annotation.DynamicMapping;
import com.meijm.basis.annotation.DynamicMappingUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Map;

@Slf4j
@Component
public class DynamicMappingStartup implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        handlerMapping.getHandlerMethods().
                entrySet().stream().
                filter(entry -> AnnotationUtils.getAnnotation(entry.getValue().getMethod(), DynamicMapping.class) != null).
                forEach(entry -> {
                    try {
                        RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
                        options.setPatternParser(new PathPatternParser());
                        String path = entry.getKey().getDirectPaths().stream().findFirst().orElse(null);
                        String beanName = (String) entry.getValue().getBean();
                        DynamicMappingUrl dmu = new DynamicMappingUrl(entry.getValue().getMethod(),applicationContext.getBean(beanName));
                        RequestMappingInfo info = RequestMappingInfo
                                .paths(path+"_mjm")
                                .methods(RequestMethod.GET)
                                .options(options)
                                .build();
                        handlerMapping.registerMapping(info,dmu,DynamicMappingUrl.class.getMethod("addMapping", Map.class));

                        RequestMappingInfo voidInfo = RequestMappingInfo
                                .paths(path+"_void")
                                .methods(RequestMethod.GET)
                                .options(options)
                                .build();
                        handlerMapping.registerMapping(voidInfo,dmu,DynamicMappingUrl.class.getMethod("addVoidMapping", Map.class));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
