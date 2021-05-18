package be.cronos.edge.service.statistics;

import be.cronos.edge.service.EdgeServiceApplication;
import be.cronos.edge.service.sensor.emitter.GasStreamEmitter;
import be.cronos.edge.service.sensor.emitter.PollutionStreamEmitter;
import be.cronos.edge.service.sensor.scheduler.GasScheduler;
import be.cronos.edge.service.sensor.scheduler.PollutionScheduler;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;

@ApplicationScoped
@RequiredArgsConstructor
public class StationStatisticsFactory {

    private final EdgeServiceApplication app;
    private final GasScheduler gasScheduler;
    private final PollutionScheduler pollutionScheduler;
    private final GasStreamEmitter gasDataEmitter;
    private final PollutionStreamEmitter pollutionDataEmitter;

    public StationStatistics collectStatistics() throws SchedulerException {
        return StationStatistics.builder()
                .station(app.getStation())
                .instant(Instant.now())
                .statistics(StationStatistics.Statistics.builder()
                        .pollution(StationStatistics.Statistics.Result.builder()
                                .successCount(pollutionDataEmitter.getSuccessCount())
                                .status(pollutionScheduler.getStatus())
                                .errorCount(pollutionDataEmitter.getErrorCount())
                                .interval(pollutionScheduler.getInterval())
                                .build())
                        .gas(StationStatistics.Statistics.Result.builder()
                                .successCount(gasDataEmitter.getSuccessCount())
                                .errorCount(gasDataEmitter.getErrorCount())
                                .status(gasScheduler.getStatus())
                                .interval(gasScheduler.getInterval())
                                .build())
                        .build())
                .build();
    }
}
