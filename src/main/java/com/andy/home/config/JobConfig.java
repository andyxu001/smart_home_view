package com.andy.home.config;


import com.andy.home.job.ProductJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JobConfig {

    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(ProductJob.class)
                .storeDurably()
                .withIdentity("product")
                .build();
    }

    @Bean
    public Trigger trigger(){
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule("0 0 18 * * ? *");
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withSchedule(cron)
                .build();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
