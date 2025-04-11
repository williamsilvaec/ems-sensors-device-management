package com.williamsilva.sensors.device.management.api.model;

import io.hypersistence.tsid.TSID;

import java.time.OffsetDateTime;

public class SensorMonitoringOuput {

    private TSID id;
    private Double lastTemperature;
    private OffsetDateTime updatedAt;
    private Boolean enabled;

    public TSID getId() {
        return id;
    }

    public void setId(TSID id) {
        this.id = id;
    }

    public Double getLastTemperature() {
        return lastTemperature;
    }

    public void setLastTemperature(Double lastTemperature) {
        this.lastTemperature = lastTemperature;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
