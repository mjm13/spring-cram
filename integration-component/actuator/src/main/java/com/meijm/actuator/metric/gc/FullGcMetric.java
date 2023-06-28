package com.meijm.actuator.metric.gc;

import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class FullGcMetric {

    public FullGcMetric(MeterRegistry registry){
        Map<String,Long> map = new HashMap<>();
        FullGCInfo info = new GCHelper().getGCInfo();
        map.put("time",info.getGcTime());
        map.put("count",info.getGcCount());
        registry.gaugeMapSize("111jvm.gc.fullgc", Tags.empty(),map);


//        GCHelper gcHelper = new GCHelper();
//        FunctionTimer.builder("plg.jvm.gc.fullgc", gcHelper, temp -> {
//                    return gcHelper.getGCInfo().getGcCount();
//                }, temp -> {
//                    return gcHelper.getGCInfo().getGcTime();
//                }, TimeUnit.MILLISECONDS)
//                .description("test CustomMetrics")
//                .register(registry);
    }

}
