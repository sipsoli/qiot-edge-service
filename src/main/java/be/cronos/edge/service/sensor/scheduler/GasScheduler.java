package be.cronos.edge.service.sensor.scheduler;

import be.cronos.edge.service.sensor.emitter.GasStreamEmitter;
import org.quartz.Job;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GasScheduler extends SensorScheduler {

    @Override
    protected String getJobDetailName() {
        return "gas-job";
    }

    @Override
    protected String getJobDetailGroup() {
        return "gas-job-group";
    }

    @Override
    protected String getJobTriggerName() {
        return "gas-trigger";
    }

    @Override
    protected String getJobTriggerGroup() {
        return "gas-trigger-group";
    }

    @Override
    protected Class<? extends Job> getJobClass() {
        return GasStreamEmitter.class;
    }

    @Override
    protected int getDelayInMilliseconds() {
        return 1000;
    }
}
