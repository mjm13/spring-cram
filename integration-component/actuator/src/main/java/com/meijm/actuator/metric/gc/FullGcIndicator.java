package com.meijm.actuator.metric.gc;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class FullGcIndicator extends AbstractHealthIndicator{
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        FullGCInfo info = GCHelper.getGCInfo();
        builder.withDetail("fullgc",info).build();
    }
}
