package be.cronos.edge.service.sensor.scheduler;

import be.cronos.edge.service.statistics.StationStatisticsEmitter;
import lombok.AllArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@AllArgsConstructor
public class SensorSchedulerListener extends JobListenerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(SensorSchedulerListener.class);

    private final StationStatisticsEmitter stationStatisticsEmitter;

    @Override
    public String getName() {
        return "sensor-scheduler-listener";
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        LOG.debug("scheduled job executed: {}", context.getTrigger());
        stationStatisticsEmitter.emit();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        LOG.debug("scheduled job ready to be executed: {}", context.getTrigger());
        super.jobToBeExecuted(context);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        LOG.debug("scheduled job vetoed");
        super.jobExecutionVetoed(context);
    }


}
