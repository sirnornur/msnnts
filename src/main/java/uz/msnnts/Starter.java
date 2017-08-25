package uz.msnnts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("uz.sspm.miskinframework")
@EnableAutoConfiguration
public class Starter {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Starter.class, args);
        System.out.println("test");
    }
}