package com.meijm.actuator.metric.custom;

import com.meijm.actuator.metric.gc.GCHelper;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("custom")
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        GCHelper gcHelper = new GCHelper();
        Map<String,Object> data = new HashMap<>();
        data.put("count",gcHelper.getGCInfo().getGcCount());
        data.put("totaltime",gcHelper.getGCInfo().getGcTime());
        Health.Builder builder = Health.up();
        builder.withDetails(data);
        return builder.build();
    }
}
