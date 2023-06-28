package com.meijm.actuator.metric.custom;

import com.meijm.actuator.metric.gc.GCHelper;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CustomMetrics implements MeterBinder {
    @Override
    public void bindTo(MeterRegistry registry) {
        GCHelper gcHelper = new GCHelper();
        FunctionTimer.builder("jvm.gc.fullgc", gcHelper, temp -> {
                    return gcHelper.getGCInfo().getGcCount();
                }, temp -> {
                    return gcHelper.getGCInfo().getGcTime();
                }, TimeUnit.MILLISECONDS)
                .description("test CustomMetrics")
                .register(registry);
    }

}
