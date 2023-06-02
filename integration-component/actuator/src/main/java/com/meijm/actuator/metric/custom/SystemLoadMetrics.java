package com.meijm.actuator.metric.custom;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Component
public class SystemLoadMetrics implements MeterBinder {

    private final OperatingSystemMXBean osBean;

    public SystemLoadMetrics() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        // 创建一个Gauge，用于记录系统平均负载
        Gauge.builder("mjm.load.average", osBean, OperatingSystemMXBean::getSystemLoadAverage)
                .description("The system load average")
                .baseUnit("load")
                .register(registry);
        Gauge.builder("mjm.load.average", osBean, OperatingSystemMXBean::getSystemLoadAverage)
                .description("The system load average")
                .baseUnit("load")
                .register(registry);
    }
}
