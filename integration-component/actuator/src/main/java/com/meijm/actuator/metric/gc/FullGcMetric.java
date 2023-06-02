package com.meijm.actuator.metric.gc;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FullGcMetric {

    public FullGcMetric(MeterRegistry registry){
        Map<String,Long> map = new HashMap<>();
        FullGCInfo info = GCHelper.getGCInfo();
        map.put("time",info.getGcTime());
        map.put("count",info.getGcCount());
        registry.gaugeMapSize("jvm.gc.fullgc", Tags.empty(),map);
    }

}
