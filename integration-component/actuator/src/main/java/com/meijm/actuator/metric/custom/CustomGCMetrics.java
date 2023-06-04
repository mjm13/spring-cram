package com.meijm.actuator.metric.custom;

import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CustomGCMetrics implements MeterBinder {
    @Override
    public void bindTo(MeterRegistry registry) {
        FunctionTimer.builder("mjm.metrics", this, value -> {
                    return 10L;
                }, value -> {
                    return 2;
                }, TimeUnit.SECONDS)
                .description("test CustomGCMetrics")
                .register(registry);
    }
}
