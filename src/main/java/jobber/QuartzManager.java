package jobber;

import com.yammer.dropwizard.lifecycle.Managed;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzManager implements Managed {

    private final Scheduler scheduler;

    public QuartzManager(Scheduler scheduler) throws SchedulerException {
        this.scheduler = scheduler;

        JobDetail job = newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(job, trigger);
    }

    @Override
    public void start() throws Exception {
        scheduler.start();
    }

    @Override
    public void stop() throws Exception {
        scheduler.shutdown();
    }

}