package be.cronos.edge.service.sensor.emitter;

import be.cronos.edge.service.StationConfiguration;
import be.cronos.edge.service.sensor.SensorService;
import be.cronos.edge.service.sensor.dto.PollutionResponse;
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
public class PollutionStreamEmitter implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(PollutionStreamEmitter.class);

    private final SensorService sensorService;
    private final ObjectMapper objectMapper;
    private final MqttClient mqttClient;
    private final StationConfiguration stationConfiguration;
    private final AtomicLong successCounter = new AtomicLong(0);
    private final AtomicLong errorCounter = new AtomicLong(0);

    public void execute(JobExecutionContext context) {
        try {
            PollutionResponse pollutionResponse = sensorService.getPollution();
            String data = objectMapper.writeValueAsString(pollutionResponse);
            String pollutionTopicName = stationConfiguration.getMqtt().getTopic().getPollutionTopic();
            int pollutionTopicQos = stationConfiguration.getMqtt().getTopic().getPollutionQos();
            if (mqttClient.isConnected()){
                LOG.info("sending pollution data to DataHub: {}", pollutionResponse);
                mqttClient.publish(pollutionTopicName, Buffer.buffer(data), MqttQoS.valueOf(pollutionTopicQos), false, false);
            } else {
                LOG.info("mqtt client not connected, not sending data");
            }
            successCounter.incrementAndGet();
        } catch (Exception e){
            LOG.error("error sending pollution data to DataHub: {}", e.getMessage());
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
