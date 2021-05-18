package be.cronos.edge.service.statistics;

import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/station")
@RequiredArgsConstructor
public class StationStatisticsResource {

    private final StationStatisticsFactory stationStatisticsFactory;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StationStatistics getStatistics() throws SchedulerException {
        return stationStatisticsFactory.collectStatistics();
    }
}
