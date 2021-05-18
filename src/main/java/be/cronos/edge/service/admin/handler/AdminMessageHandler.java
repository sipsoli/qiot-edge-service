package be.cronos.edge.service.admin.handler;

import be.cronos.edge.service.admin.dto.Event;
import be.cronos.edge.service.admin.dto.Interval;
import be.cronos.edge.service.sensor.scheduler.GasScheduler;
import be.cronos.edge.service.sensor.scheduler.PollutionScheduler;
import be.cronos.edge.service.statistics.StationStatisticsEmitter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
public class AdminMessageHandler implements MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AdminMessageHandler.class);

    private static final String PAUSE_GAS_COLLECTOR = "pauseGasCollector";
    private static final String RESUME_GAS_COLLECTOR = "resumeGasCollector";
    private static final String CHANGE_INTERVAL_GAS_COLLECTOR = "changeIntervalGasCollector";

    private static final String PAUSE_POLLUTION_COLLECTOR = "pausePollutionCollector";
    private static final String RESUME_POLLUTION_COLLECTOR = "resumePollutionCollector";
    private static final String CHANGE_INTERVAL_POLLUTION_COLLECTOR = "changeIntervalPollutionCollector";

    private final GasScheduler gasScheduler;
    private final PollutionScheduler pollutionScheduler;
    private final ObjectMapper objectMapper;
    private final StationStatisticsEmitter stationStatisticsEmitter;

    @Override
    @SneakyThrows
    public void handleMessage(String message) {
        Event event = new ObjectMapper().readValue(message, Event.class);
        LOG.info("handling message: {}", event);
        switch (event.getAction()){
            case PAUSE_GAS_COLLECTOR:
                gasScheduler.pause();
                break;
            case RESUME_GAS_COLLECTOR:
                gasScheduler.resume();
                break;
            case CHANGE_INTERVAL_GAS_COLLECTOR:
                Interval gasInterval = objectMapper.readValue(event.getPayload(), Interval.class);
                LOG.info("interval: " + gasInterval);
                gasScheduler.updateInterval(gasInterval.getInterval());
                break;
            case PAUSE_POLLUTION_COLLECTOR:
                pollutionScheduler.pause();
                break;
            case RESUME_POLLUTION_COLLECTOR:
                pollutionScheduler.resume();
                break;
            case CHANGE_INTERVAL_POLLUTION_COLLECTOR:
                Interval pollutionInterval = objectMapper.readValue(event.getPayload(), Interval.class);
                LOG.info("interval: " + pollutionInterval);
                pollutionScheduler.updateInterval(pollutionInterval.getInterval());
                break;
        }
        stationStatisticsEmitter.emit();
    }

}
