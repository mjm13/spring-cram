package com.meijm.actuator.metric.custom;

import com.meijm.actuator.metric.gc.GCHelper;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("custom")
public class CustomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder)  {
        GCHelper gcHelper = new GCHelper();
        Map<String,Object> data = new HashMap<>();
        data.put("count",gcHelper.getGCInfo().getGcCount());
        data.put("totaltime",gcHelper.getGCInfo().getGcTime());
        builder.up().
        withDetails(data).
        build();
    }
}
