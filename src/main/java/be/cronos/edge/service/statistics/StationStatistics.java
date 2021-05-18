package be.cronos.edge.service.statistics;

import be.cronos.edge.service.Station;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class StationStatistics {

    Instant instant;
    Station station;
    Statistics statistics;

    @Value
    @Builder
    public static class Statistics {

        Result gas;
        Result pollution;

        @Value
        @Builder
        static class Result {
            long successCount;
            long errorCount;
            String status;
            long interval;
        }
    }
}
