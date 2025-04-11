package com.williamsilva.sensors.device.management.api.client.impl;

import com.williamsilva.sensors.device.management.api.client.RestClientFactory;
import com.williamsilva.sensors.device.management.api.client.SensorMonitoringClient;
import com.williamsilva.sensors.device.management.api.model.SensorMonitoringOuput;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

//@Component
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;

    public SensorMonitoringClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
    }

    @Override
    public void enableMonitoring(TSID sensorId) {
        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void disableMonitoring(TSID sensorId) {
        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public SensorMonitoringOuput getDetail(TSID sensorId) {
        return restClient.get()
                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
                .retrieve()
                .body(SensorMonitoringOuput.class);
    }
}
