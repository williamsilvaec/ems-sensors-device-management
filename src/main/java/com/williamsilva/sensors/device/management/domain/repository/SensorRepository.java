package com.williamsilva.sensors.device.management.domain.repository;

import com.williamsilva.sensors.device.management.domain.model.Sensor;
import com.williamsilva.sensors.device.management.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, SensorId> {
}
