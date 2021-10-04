package com.seungpang.testspringbatch.player;

import com.seungpang.testspringbatch.core.service.PlayerSalaryService;
import com.seungpang.testspringbatch.dto.PlayerDto;
import com.seungpang.testspringbatch.dto.PlayerSalaryDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
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
        ItemProcessorAdapter<PlayerDto, PlayerSalaryDto> playerSalaryDtoItemProcessorAdapter) {
        return stepBuilderFactory.get("flatFileStep")
            .<PlayerDto, PlayerSalaryDto>chunk(5)
            .reader(playerFileItemReader)
            .processor(playerSalaryDtoItemProcessorAdapter)
            .writer(new ItemWriter<PlayerSalaryDto>() {
                @Override
                public void write(List<? extends PlayerSalaryDto> items) throws Exception {
                    items.forEach(System.out::println);
                }
            })
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
