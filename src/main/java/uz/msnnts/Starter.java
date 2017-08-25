package uz.msnnts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("uz.msnnts")
@EnableAutoConfiguration
@EnableScheduling
public class Starter {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Starter.class, args);
        StatisticsService bean = applicationContext.getBean(StatisticsService.class);
        System.out.println("test");
    }
}