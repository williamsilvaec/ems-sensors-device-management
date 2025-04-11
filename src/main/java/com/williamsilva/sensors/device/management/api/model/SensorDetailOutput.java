package com.williamsilva.sensors.device.management.api.model;

public class SensorDetailOutput {

    private final SensorOutput sensor;
    private final SensorMonitoringOuput monitoring;

    private SensorDetailOutput(SensorOutput sensor, SensorMonitoringOuput monitoring) {
        this.sensor = sensor;
        this.monitoring = monitoring;
    }

    public static SensorDetailOutput from(SensorOutput sensor, SensorMonitoringOuput monitoring) {
        return new SensorDetailOutput(sensor, monitoring);
    }

    public SensorOutput getSensor() {
        return sensor;
    }

    public SensorMonitoringOuput getMonitoring() {
        return monitoring;
    }
}
