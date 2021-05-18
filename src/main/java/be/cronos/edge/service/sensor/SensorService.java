package be.cronos.edge.service.sensor;

import be.cronos.edge.service.EdgeServiceApplication;
import be.cronos.edge.service.sensor.dto.GasResponse;
import be.cronos.edge.service.sensor.dto.PollutionResponse;
import be.cronos.edge.service.sensor.dto.SystemIdResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class SensorService {

    private static final Logger LOG = LoggerFactory.getLogger(SensorService.class);

    private final SensorServiceClient sensorServiceClient;
    private final EdgeServiceApplication app;
    private final ObjectMapper objectMapper;

    public SensorService(@RestClient SensorServiceClient sensorServiceClient, EdgeServiceApplication app, ObjectMapper objectMapper) {
        this.sensorServiceClient = sensorServiceClient;
        this.app = app;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public GasResponse getGas() {
        GasResponse gasResponse = objectMapper.readValue(sensorServiceClient.getGas(), GasResponse.class);
        gasResponse.setStationId(app.getStation().getId());
        gasResponse.setInstant(Instant.now());
        return gasResponse;
    }

    @SneakyThrows
    public PollutionResponse getPollution() {
        PollutionResponse pollutionResponse = objectMapper.readValue(sensorServiceClient.getPollution(), PollutionResponse.class);
        pollutionResponse.setStationId(app.getStation().getId());
        pollutionResponse.setInstant(Instant.now());
        return pollutionResponse;
    }

    public String getSerial() {
        try {
            return sensorServiceClient.getSerial().getId();
        } catch (Exception e){
            LOG.warn("could not obtain serial, probably service is not running. using auto generated uuid. {}", e.getMessage());
            return UUID.randomUUID().toString();
        }
    }

}
