package com.seungpang.testspringbatch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean("testJob")
    public Job helloJob(Step testStep) {
        return jobBuilderFactory.get("testJob")
            .incrementer(new RunIdIncrementer())
            .start(testStep)
            .build();
    }

    @JobScope
    @Bean("testStep")
    public Step testStep(Tasklet tasklet) {
        return stepBuilderFactory.get("testStep")
            .tasklet(tasklet)
            .build();
    }

    @StepScope
    @Bean
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("Test Spring Batch");
            return RepeatStatus.FINISHED;
        };
    }
}
