package be.cronos.edge.service.context;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

import javax.enterprise.context.ApplicationScoped;

import static be.cronos.edge.service.context.CurrentContext.cleanContext;

@ApplicationScoped
public class ClearContextJobListener extends JobListenerSupport {

    @Override
    public String getName() {
        return "clear-context-station-job-listener";
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        cleanContext();
    }
}
