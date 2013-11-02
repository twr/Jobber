package jobber;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class Jobber extends Service<JobberConfiguration> {

    public static void main(String[] args) throws Exception {
        new Jobber().run(args);
    }

    @Override
    public void initialize(Bootstrap<JobberConfiguration> bootstrap) {
        bootstrap.setName("jobber");
    }

    @Override
    public void run(JobberConfiguration configuration, Environment environment) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        environment.manage(new QuartzManager(scheduler));

        environment.addResource(new DashboardResource(scheduler));
    }

}
