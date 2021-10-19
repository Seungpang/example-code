package com.seungpang.testspringbatch.job.parallel;

import com.seungpang.testspringbatch.dto.AmountDto;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

/**
 * 단일 프로세스 멀티 쓰레드에서 flow를 사용해 스텝을 동시에 실행한다.
 */
@Configuration
@AllArgsConstructor
public class ParallelStepJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parallelJob(Flow splitFlow) {
        return jobBuilderFactory.get("parallelJob")
            .incrementer(new RunIdIncrementer())
            .start(splitFlow)
            .build()
            .build();
    }

    @Bean
    public Flow splitFlow(TaskExecutor taskExecutor,
        Flow flowAmountFileStep,
        Flow flowAnotherStep) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
            .split(taskExecutor)
            .add(flowAmountFileStep, flowAnotherStep)
            .build();
    }

    @Bean
    public Flow flowAmountFileStep(Step amountFileStep) {
        return new FlowBuilder<SimpleFlow>("flowAmountFileStep")
            .start(amountFileStep)
            .end();
    }

    @Bean
    public Step amountFileStep(FlatFileItemReader<AmountDto> amountFileItemReader,
        ItemProcessor<AmountDto, AmountDto> amountFileItemProcessor,
        FlatFileItemWriter<AmountDto> amoutFileItemWriter
    ) {
        return stepBuilderFactory.get("multiThreadStep")
            .<AmountDto, AmountDto>chunk(10)
            .reader(amountFileItemReader)
            .processor(amountFileItemProcessor)
            .writer(amoutFileItemWriter)
            .build();
    }

    @Bean
    public Flow flowAnotherStep(Step anotherStep) {
        return new FlowBuilder<SimpleFlow>("flowAnotherStep")
            .start(anotherStep)
            .build();
    }

    @Bean
    public Step anotherStep() {
        return stepBuilderFactory.get("anotherStep")
            .tasklet((contribution, chunkContext) -> {
                Thread.sleep(500);
                System.out.println(
                    "Another Step Completed. Thread = " + Thread.currentThread().getName());
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
