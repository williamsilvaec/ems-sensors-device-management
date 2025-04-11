package com.williamsilva.sensors.device.management.api.controller;

import com.williamsilva.sensors.device.management.api.client.SensorMonitoringClient;
import com.williamsilva.sensors.device.management.api.model.SensorDetailOutput;
import com.williamsilva.sensors.device.management.api.model.SensorInput;
import com.williamsilva.sensors.device.management.api.model.SensorMonitoringOuput;
import com.williamsilva.sensors.device.management.api.model.SensorOutput;
import com.williamsilva.sensors.device.management.common.IdGenerator;
import com.williamsilva.sensors.device.management.domain.model.Sensor;
import com.williamsilva.sensors.device.management.domain.model.SensorId;
import com.williamsilva.sensors.device.management.domain.repository.SensorRepository;
import com.williamsilva.sensors.device.management.domain.service.SensorService;
import io.hypersistence.tsid.TSID;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorRepository sensorRepository;
    private final SensorService sensorService;
    private final SensorMonitoringClient sensorMonitoringClient;

    public SensorController(SensorRepository sensorRepository, SensorService sensorService,
                            SensorMonitoringClient sensorMonitoringClient) {
        this.sensorRepository = sensorRepository;
        this.sensorService = sensorService;
        this.sensorMonitoringClient = sensorMonitoringClient;
    }

    @GetMapping
    public Page<SensorOutput> search(@PageableDefault(size = 5) Pageable pageable) {
        Page<Sensor> sensors = sensorRepository.findAll(pageable);
        return sensors.map(SensorOutput::from);
    }

    @GetMapping("{sensorId}")
    public SensorOutput getOne(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return SensorOutput.from(sensor);
    }

    @GetMapping("{sensorId}/detail")
    public SensorDetailOutput getOneWithDetail(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SensorOutput sensorOutput = SensorOutput.from(sensor);
        SensorMonitoringOuput monitoringOuput = sensorMonitoringClient.getDetail(sensorId);

        return SensorDetailOutput.from(sensorOutput, monitoringOuput);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput save(@RequestBody SensorInput sensorInput) {
        Sensor sensor = Sensor.from(sensorInput);
        SensorId sensorId = new SensorId(IdGenerator.generateTSID());
        sensor.setId(sensorId);
        sensor = sensorRepository.save(sensor);
        return SensorOutput.from(sensor);
    }

    @PutMapping("{sensorId}")
    public SensorOutput update(@PathVariable TSID sensorId, @RequestBody SensorInput sensorInput) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BeanUtils.copyProperties(sensorInput, sensor, "id");
        sensor = sensorRepository.save(sensor);

        return SensorOutput.from(sensor);
    }

    @DeleteMapping("{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId) {
        SensorId id = new SensorId(sensorId);
        if (!sensorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        sensorRepository.deleteById(id);
    }

    @PutMapping("{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable TSID sensorId) {
        try {

            sensorService.enableSensor(new SensorId(sensorId));

        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        sensorMonitoringClient.enableMonitoring(sensorId);
    }

    @DeleteMapping("{sensorId}/enable")
    public ResponseEntity<?> disable(@PathVariable TSID sensorId) {
        try {
            sensorService.disableSensor(new SensorId(sensorId));
            sensorMonitoringClient.disableMonitoring(sensorId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
