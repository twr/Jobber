package jobber;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/jobs")
@Produces(MediaType.APPLICATION_JSON)
public class DashboardResource {

    private final Scheduler scheduler;

    public DashboardResource(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @GET
    public String listAllJobs() throws SchedulerException {
        StringBuilder result = new StringBuilder();

        for (String group : scheduler.getJobGroupNames()) {
            result.append("[").append(group).append("]").append("\n");

            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))) {
                result.append(" - ").append(jobKey);
                List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggersOfJob) {
                    result.append(" last time executed at ").append(trigger.getPreviousFireTime()).append(" by ").append(trigger.getKey()).append("\n");
                }
                result.append("\n");
            }
        }

        return result.toString();
    }

}
