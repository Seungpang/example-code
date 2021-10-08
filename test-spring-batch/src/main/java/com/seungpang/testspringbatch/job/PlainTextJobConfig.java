package com.seungpang.testspringbatch.job;

import com.seungpang.testspringbatch.core.domain.PlainText;
import com.seungpang.testspringbatch.core.domain.ResultText;
import com.seungpang.testspringbatch.core.repository.PlainTextRepository;
import com.seungpang.testspringbatch.core.repository.ResultTextRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Configuration
@RequiredArgsConstructor
public class PlainTextJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PlainTextRepository plainTextRepository;
    private final ResultTextRepository resultTextRepository;

    @Bean("plainTextJob")
    public Job helloJob(Step plainTextStep) {
        return jobBuilderFactory.get("plainTextJob")
            .incrementer(new RunIdIncrementer())
            .start(plainTextStep)
            .build();
    }

    @JobScope
    @Bean("plainTextStep")
    public Step plainTextStep(ItemReader plainTextReader,
        ItemProcessor plainTextProcessor,
        ItemWriter plainTextWriter) {
        return stepBuilderFactory.get("plainTextStep")
            .<PlainText, String>chunk(5)
            .reader(plainTextReader)
            .processor(plainTextProcessor)
            .writer(plainTextWriter)
            .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<PlainText> plainTextReader() {
        return new RepositoryItemReaderBuilder<PlainText>()
            .name("plainTextReader")
            .repository(plainTextRepository)
            .methodName("findBy")
            .pageSize(5)
            .arguments(List.of())
            .sorts(Collections.singletonMap("id", Direction.DESC))
            .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<PlainText, String> plainTextProcessor() {
        return item -> "processed " + item.getText();
    }

    @StepScope
    @Bean
    public ItemWriter<String> plainTextWriter() {
        return items -> {
            items.forEach(item -> resultTextRepository.save(new ResultText(null, item)));
            System.out.println("=== chunk is finished");
        };
    }

}
