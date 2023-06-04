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
//        Map<String,Long> map = new HashMap<>();
//        FullGCInfo info = GCHelper.getGCInfo();
//        map.put("time",info.getGcTime());
//        map.put("count",info.getGcCount());
//        registry.gaugeMapSize("jvm.gc.fullgc", Tags.empty(),map);
        FullGCInfo info = new FullGCInfo();
        FunctionTimer.builder("plg.jvm.gc.fullgc", info, temp -> {
                    return temp.getGcCount();
                }, temp -> {
                    return temp.getGcTime();
                }, TimeUnit.MILLISECONDS)
                .description("test CustomGCMetrics")
                .register(registry);
    }

}
