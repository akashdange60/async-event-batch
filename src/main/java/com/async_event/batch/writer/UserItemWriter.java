package com.async_event.batch.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.async_event.batch.model.UserReport;

@Configuration
public class UserItemWriter {
	
	
	@Bean
     FlatFileItemWriter<UserReport> userWriter() {

        return new FlatFileItemWriterBuilder<UserReport>()
                .name("userWriter")
                .resource(new FileSystemResource("reports/user-report.csv"))
                .headerCallback(writer -> writer.write("ID,NAME,EMAIL"))
                .lineAggregator(user ->
                        user.getId() + "," +
                        user.getName() + "," +
                        user.getEmail()
                )
                .build();
    }

}
