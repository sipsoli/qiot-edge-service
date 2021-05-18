package be.cronos.edge.service.sensor.scheduler;

import be.cronos.edge.service.context.ClearContextJobListener;
import io.quarkus.runtime.StartupEvent;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

public abstract class SensorScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(SensorScheduler.class);
    private static final int DEFAULT_INTERVAL_MILLISECONDS = 5000;

    @Inject
    Scheduler quartz;

    @Inject
    ClearContextJobListener clearContextJobListener;

    @Inject
    SensorSchedulerListener sensorSchedulerListener;

    void onStart(@Observes StartupEvent event) throws SchedulerException {
        JobDetail job = buildJobDetail();
        Trigger trigger = buildTrigger(getDefaultInterval());
        LOG.debug("scheduled job: {}", trigger);
        quartz.scheduleJob(job, trigger);
        quartz.getListenerManager().addJobListener(clearContextJobListener);
        quartz.getListenerManager().addJobListener(sensorSchedulerListener);
    }

    public void updateInterval(int milliseconds) throws SchedulerException {
        Trigger newTrigger = buildTrigger(milliseconds);
        Trigger oldTrigger = quartz.getTrigger(getTriggerKey());
        quartz.rescheduleJob(oldTrigger.getKey(), newTrigger);
        LOG.info("rescheduled job. new interval {} milliseconds", milliseconds);
    }

    public void pause() throws SchedulerException {
        quartz.pauseTrigger(getTriggerKey());
        LOG.info(getTriggerKey().getName() + " " + getTriggerKey().getGroup());
        LOG.info("pausing scheduler {}", getJobClass());
    }

    public void resume() throws SchedulerException {
        quartz.resumeTrigger(getTriggerKey());
        LOG.info("resuming scheduler {}", getJobClass());
    }

    public String getStatus() throws SchedulerException {
        Trigger.TriggerState triggerState = quartz.getTriggerState(getTriggerKey());
        return String.valueOf(triggerState);
    }

    public long getInterval() throws SchedulerException {
        SimpleTrigger simpleTrigger = (SimpleTrigger) quartz.getTrigger(getTriggerKey());
        return simpleTrigger.getRepeatInterval();
    }

    protected abstract String getJobDetailName();

    protected abstract String getJobDetailGroup();

    protected abstract String getJobTriggerName();

    protected abstract String getJobTriggerGroup();

    protected abstract Class<? extends Job> getJobClass();

    protected int getDefaultInterval(){
        return DEFAULT_INTERVAL_MILLISECONDS;
    }

    protected JobDetail buildJobDetail(){
        return JobBuilder.newJob(getJobClass())
                .withIdentity(getJobKey())
                .build();
    }

    protected Trigger buildTrigger(int seconds){
        return TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey())
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMilliseconds(seconds)
                                .repeatForever())
                .startAt(nowPlusDelay(getDelayInMilliseconds()))
                .build();
    }

    protected abstract int getDelayInMilliseconds();

    private Date nowPlusDelay(int defaultDelayMilliseconds){
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis() + defaultDelayMilliseconds);
        return now.getTime();
    }

    private JobKey getJobKey(){
        return new JobKey(getJobDetailName(), getJobDetailGroup());
    }

    private TriggerKey getTriggerKey(){
        return new TriggerKey(getJobTriggerName(), getJobTriggerGroup());
    }

}
