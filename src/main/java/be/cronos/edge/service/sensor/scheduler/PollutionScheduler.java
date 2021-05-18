package be.cronos.edge.service.sensor.scheduler;

import be.cronos.edge.service.sensor.emitter.PollutionStreamEmitter;
import org.quartz.Job;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PollutionScheduler extends SensorScheduler {

    @Override
    protected String getJobDetailName() {
        return "pollution-job";
    }

    @Override
    protected String getJobDetailGroup() {
        return "pollution-job-group";
    }

    @Override
    protected String getJobTriggerName() {
        return "pollution-trigger";
    }

    @Override
    protected String getJobTriggerGroup() {
        return "pollution-trigger-group";
    }

    @Override
    protected Class<? extends Job> getJobClass() {
        return PollutionStreamEmitter.class;
    }

    @Override
    protected int getDelayInMilliseconds() {
        return 3000;
    }
}
