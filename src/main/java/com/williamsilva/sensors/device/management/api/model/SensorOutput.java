package com.williamsilva.sensors.device.management.api.model;

import com.williamsilva.sensors.device.management.domain.model.Sensor;

public class SensorOutput {

    private final String id;
    private final String name;
    private final String location;
    private final String ip;
    private final String protocol;
    private final String model;
    private final Boolean enabled;

    private SensorOutput(String id, String name, String location, String ip, String protocol, String model,
                         Boolean enabled) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.ip = ip;
        this.protocol = protocol;
        this.model = model;
        this.enabled = enabled;
    }

    public static SensorOutput from(Sensor sensor) {
        return new SensorOutput(
                sensor.getId().toString(),
                sensor.getName(),
                sensor.getLocation(),
                sensor.getIp(),
                sensor.getProtocol(),
                sensor.getModel(),
                sensor.getEnabled()
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getIp() {
        return ip;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getModel() {
        return model;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
