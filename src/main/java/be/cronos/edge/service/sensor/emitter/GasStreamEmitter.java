package be.cronos.edge.service.sensor.emitter;

import be.cronos.edge.service.StationConfiguration;
import be.cronos.edge.service.sensor.SensorService;
import be.cronos.edge.service.sensor.dto.GasResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@RequiredArgsConstructor
public class GasStreamEmitter implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(GasStreamEmitter.class);

    private final SensorService sensorService;
    private final MqttClient mqttClient;
    private final ObjectMapper objectMapper;
    private final StationConfiguration stationConfiguration;
    private final AtomicLong successCounter = new AtomicLong(0);
    private final AtomicLong errorCounter = new AtomicLong(0);

    public void execute(JobExecutionContext context) {
        try {
            GasResponse gasResponse = sensorService.getGas();
            String data = objectMapper.writeValueAsString(gasResponse);
            String gasTopicName = stationConfiguration.getMqtt().getTopic().getGasTopic();
            int gasQos = stationConfiguration.getMqtt().getTopic().getGasQos();
            if (mqttClient.isConnected()){
                LOG.info("sending gas data to DataHub: {}", gasResponse);
                mqttClient.publish(gasTopicName, Buffer.buffer(data), MqttQoS.valueOf(gasQos), false, false);
            } else {
                LOG.info("mqtt client not connected, not sending data");
            }
            successCounter.incrementAndGet();
        } catch (Exception e){
            LOG.error("error sending gas data to DataHub: {}", e.getMessage());
            errorCounter.incrementAndGet();
        }
    }

    public long getSuccessCount() {
        return successCounter.get();
    }

    public long getErrorCount() {
        return errorCounter.get();
    }

}
