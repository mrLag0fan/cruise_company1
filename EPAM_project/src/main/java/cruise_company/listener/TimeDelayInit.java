package cruise_company.listener;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class TimeDelayInit
 *
 */
public class TimeDelayInit implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public TimeDelayInit() {
        // TODO Auto-generated constructor stub
    }
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	new EndOfTourCheck().run();
    	ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
    	ZonedDateTime nextRun = now.withHour(5).withMinute(0).withSecond(0);
    	if(now.compareTo(nextRun) > 0)
    	    nextRun = nextRun.plusDays(1);

    	Duration duration = Duration.between(now, nextRun);
    	long initialDelay = duration.getSeconds();

    	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);            
    	scheduler.scheduleAtFixedRate(new EndOfTourCheck(),
    	    initialDelay,
    	    TimeUnit.DAYS.toSeconds(1),
    	    TimeUnit.SECONDS);
    }
}
