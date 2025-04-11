package com.williamsilva.sensors.device.management.domain.service;

import com.williamsilva.sensors.device.management.domain.model.Sensor;
import com.williamsilva.sensors.device.management.domain.model.SensorId;
import com.williamsilva.sensors.device.management.domain.repository.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor findById(SensorId sensorId) {
        return sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Sensor de id %s inexistente", sensorId)));
    }

    @Transactional
    public void enableSensor(SensorId sensorId) {
        Sensor sensor = findById(sensorId);
        sensor.enabled();
    }

    @Transactional
    public void disableSensor(SensorId sensorId) {
        Sensor sensor = findById(sensorId);
        sensor.disabled();
    }
}
