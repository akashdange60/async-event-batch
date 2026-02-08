package com.async_event.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.async_event.batch.model.UserReport;

@Configuration
public class UserItemReader {
	
	@Bean
     JdbcCursorItemReader<UserReport> userReader(DataSource dataSource) {

		 return new JdbcCursorItemReaderBuilder<UserReport>()
		            .name("userReader")
		            .dataSource(dataSource)
		            .sql("SELECT id, name, email FROM users")
		            .rowMapper((rs, rowNum) ->
		                    new UserReport(
		                            rs.getLong("id"),
		                            rs.getString("name"),
		                            rs.getString("email")
		                    ))
		            .build();
	}

}
