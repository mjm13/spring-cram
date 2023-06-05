package com.meijm.actuator.metric.custom;

import com.meijm.actuator.metric.gc.GCHelper;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("custom2")
public class Custom2HealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder)  {
        builder.withDetail("data","data");
        builder.build();
    }
}
