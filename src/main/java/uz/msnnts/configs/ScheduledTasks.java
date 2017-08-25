package uz.msnnts.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import uz.msnnts.services.StatisticsService;

/**
 * Created by sirnornur on 26.08.17.
 */
@Configuration
public class ScheduledTasks {

    @Autowired
    private StatisticsService statisticsService;

    @Scheduled(cron = "* * * * * *")
    public void runEverySecond(){
        statisticsService.nextSecond();
    }
}
