package be.cronos.edge.service.statistics;

import be.cronos.edge.service.admin.websocket.WebsocketManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@AllArgsConstructor
public class StationStatisticsEmitter {

    private static final Logger LOG = LoggerFactory.getLogger(StationStatisticsEmitter.class);

    private final StationStatisticsFactory stationStatisticsFactory;
    private final WebsocketManager websocketManager;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void emit(){
        LOG.debug("emitting statistics through websocket channel");
        StationStatistics statistics = stationStatisticsFactory.collectStatistics();
        String json = objectMapper.writeValueAsString(statistics);
        websocketManager.sendMessage(json);
    }
}
