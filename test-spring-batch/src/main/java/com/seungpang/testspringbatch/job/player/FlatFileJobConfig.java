package com.seungpang.testspringbatch.job.player;

import com.seungpang.testspringbatch.core.service.PlayerSalaryService;
import com.seungpang.testspringbatch.dto.PlayerDto;
import com.seungpang.testspringbatch.dto.PlayerSalaryDto;
import java.io.File;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@AllArgsConstructor
public class FlatFileJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flatFileJob(Step flatFileStep) {
        return jobBuilderFactory.get("flatFileJob")
            .incrementer(new RunIdIncrementer())
            .start(flatFileStep)
            .build();
    }

    @JobScope
    @Bean
    public Step flatFileStep(FlatFileItemReader<PlayerDto> playerFileItemReader,
        ItemProcessorAdapter<PlayerDto, PlayerSalaryDto> playerSalaryDtoItemProcessorAdapter,
        FlatFileItemWriter<PlayerSalaryDto> playerfileItemWriter) {
        return stepBuilderFactory.get("flatFileStep")
            .<PlayerDto, PlayerSalaryDto>chunk(5)
            .reader(playerFileItemReader)
            .processor(playerSalaryDtoItemProcessorAdapter)
            .writer(playerfileItemWriter)
            .build();
    }

    @StepScope
    @Bean
    public FlatFileItemWriter<PlayerSalaryDto> playerfileItemWriter() throws IOException {

        BeanWrapperFieldExtractor<PlayerSalaryDto> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"ID", "firstName", "lastName", "salary"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<PlayerSalaryDto> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter("\t");
        lineAggregator.setFieldExtractor(fieldExtractor);

        new File("player-salary-list.txt").createNewFile();
        FileSystemResource resource = new FileSystemResource("player-salary-list.txt");

        return new FlatFileItemWriterBuilder<PlayerSalaryDto>()
            .name("playerfileItemWriter")
            .resource(resource)
            .lineAggregator(lineAggregator)
            .build();
    }

    @StepScope
    @Bean
    public ItemProcessorAdapter<PlayerDto, PlayerSalaryDto> playerSalaryItemProcessorAdapter(
        PlayerSalaryService playerSalaryService
    ) {
        ItemProcessorAdapter<PlayerDto, PlayerSalaryDto> adapter = new ItemProcessorAdapter<>();
        adapter.setTargetObject(playerSalaryService);
        adapter.setTargetMethod("calcSalary");
        return adapter;
    }

    @StepScope
    @Bean
    public ItemProcessor<PlayerDto, PlayerSalaryDto> playerSalaryItemProcessor(
        PlayerSalaryService playerSalaryService
    ) {
        return new ItemProcessor<PlayerDto, PlayerSalaryDto>() {
            @Override
            public PlayerSalaryDto process(PlayerDto item) throws Exception {
                return playerSalaryService.calcSalary(item);
            }
        };
    }

    @StepScope
    @Bean
    public FlatFileItemReader<PlayerDto> playerFileItemReader() {
        return new FlatFileItemReaderBuilder<PlayerDto>()
            .name("playerFileItemReader")
            .lineTokenizer(new DelimitedLineTokenizer())
            .linesToSkip(1)
            .fieldSetMapper(new PlayerFieldSetMapper())
            .resource(new FileSystemResource("player-list.txt"))
            .build();
    }
}
