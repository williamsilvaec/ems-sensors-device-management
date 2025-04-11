package com.williamsilva.sensors.device.management.api.model;

import com.williamsilva.sensors.device.management.domain.model.Sensor;

public record SensorInput(
        String name,
        String location,
        String ip,
        String protocol,
        String model
) {

    public static SensorInput from(Sensor sensor) {
        return new SensorInput(
                sensor.getName(),
                sensor.getLocation(),
                sensor.getIp(),
                sensor.getProtocol(),
                sensor.getModel()
        );
    }
}
