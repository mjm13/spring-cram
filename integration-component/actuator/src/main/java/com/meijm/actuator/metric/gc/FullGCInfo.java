package com.meijm.actuator.metric.gc;

public class FullGCInfo {
    private long gcCount;
    private long gcTime;

    public FullGCInfo(long gcCount, long gcTime) {
        this.gcCount = gcCount;
        this.gcTime = gcTime;
    }

    public FullGCInfo() {
        this.gcCount = 0;
        this.gcTime = 0;
    }
    public long getGcCount() {
        return gcCount;
    }

    public void setGcCount(long gcCount) {
        this.gcCount = gcCount;
    }

    public long getGcTime() {
        return gcTime;
    }

    public void setGcTime(long gcTime) {
        this.gcTime = gcTime;
    }
}
