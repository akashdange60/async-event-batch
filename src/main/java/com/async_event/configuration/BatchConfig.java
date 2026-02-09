package com.async_event.configuration;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.async_event.batch.model.UserReport;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	 @Bean
	     TaskExecutor batchTaskExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(3);
	        executor.setMaxPoolSize(5);
	        executor.setThreadNamePrefix("Batch-");
	        executor.initialize();
	        return executor;
	    }
	    
	 @Bean
	 Step userReportStep(
	         JobRepository jobRepository,
	         PlatformTransactionManager transactionManager,
	         ItemReader<UserReport> reader,
	         ItemWriter<UserReport> writer) {

	     return new StepBuilder("userReportStep", jobRepository)
	             .<UserReport, UserReport>chunk(10, transactionManager)
	             .reader(reader)
	             .writer(writer)
	             .build();
	 }

	    @Bean
	   Job userReportJob(JobRepository jobRepository, Step userReportStep) {
	        return new JobBuilder("userReportJob", jobRepository)
	                .start(userReportStep)
	                .build();
	    }

}
