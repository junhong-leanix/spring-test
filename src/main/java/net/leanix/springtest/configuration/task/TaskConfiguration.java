package net.leanix.springtest.configuration.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("task")
@EnableTask
public class TaskConfiguration {

    @Bean
    public CommandLineRunner commandLineRunner() {
        return strs -> System.out.println("Task run!");
    }

}
