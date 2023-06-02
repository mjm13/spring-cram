package com.meijm.actuator.metric.gc;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GCHelper {

    public static FullGCInfo getGCInfo(){
        List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean b : list){
            if(GcGenerationAge.fromName(b.getName()) == GcGenerationAge.OLD){
                FullGCInfo info = new FullGCInfo(b.getCollectionCount(),b.getCollectionTime());
                return info;
            }
        }
        return null;
    }

}
