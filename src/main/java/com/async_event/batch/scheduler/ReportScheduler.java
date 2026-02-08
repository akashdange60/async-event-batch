package com.async_event.batch.scheduler;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportScheduler {
	
	private final JobLauncher jobLauncher;
    private final Job userReportJob;

    public ReportScheduler(JobLauncher jobLauncher, Job userReportJob) {
        this.jobLauncher = jobLauncher;
        this.userReportJob = userReportJob;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void runJob() throws Exception {
        jobLauncher.run(
                userReportJob,
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters()
        );
    }

}
