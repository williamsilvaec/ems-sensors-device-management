package com.williamsilva.sensors.device.management.domain.model;

import com.williamsilva.sensors.device.management.api.model.SensorInput;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Sensor {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
    private SensorId id;
    private String name;
    private String location;
    private String ip;
    private String protocol;
    private String model;
    private Boolean enabled;

    protected Sensor() {
    }

    private Sensor(String name, String location, String ip, String protocol, String model, Boolean enabled) {
        this.name = name;
        this.location = location;
        this.ip = ip;
        this.protocol = protocol;
        this.model = model;
        this.enabled = enabled;
    }

    public static Sensor from(SensorInput sensorInput) {
        return new Sensor(sensorInput.name(),
                sensorInput.location(),
                sensorInput.ip(),
                sensorInput.protocol(),
                sensorInput.model(),
                false);
    }

    public SensorId getId() {
        return id;
    }

    public void setId(SensorId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sensor sensor)) return false;
        return Objects.equals(id, sensor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void enabled() {
        this.enabled = true;
    }

    public void disabled() {
        this.enabled = false;
    }
}
