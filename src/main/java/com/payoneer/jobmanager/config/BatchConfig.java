package com.payoneer.jobmanager.config;

import com.payoneer.jobmanager.decider.JobFlowDecider;
import com.payoneer.jobmanager.domain.job.JobEntity;
import com.payoneer.jobmanager.execution.JobDelegator;
import com.payoneer.jobmanager.execution.JobExecutionService;
import com.payoneer.jobmanager.tasklet.ExecuteJobsTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JobConfig.class)
public class BatchConfig extends DefaultBatchConfigurer {

  private final JobBuilderFactory jobBuilder;
  private final StepBuilderFactory stepBuilder;
  private final JobConfig jobConfig;

  @Override
  public void setDataSource(DataSource dataSource) {}

  @Bean
  public Job jobManager() throws Exception {
    return jobBuilder
        .get("jobManager")
        .listener(decideJobsFlow())
        .start(executeJobsStep())
        .next(decideJobsFlow())
        .on("CONTINUE")
        .to(executeJobsStep())
        .from(decideJobsFlow())
        .on("COMPLETED")
        .end()
        .build()
        .build();
  }

  @Bean
  JobExecutionDecider decideJobsFlow() {
    return new JobFlowDecider(jobConfig);
  }

  @Bean
  Step executeJobsStep() {
    return stepBuilder
        .get("executeJobsStep")
        .allowStartIfComplete(true)
        .tasklet(executeJobsTasklet())
        .build();
  }

  @Bean
  JobDelegator jobDelegator() {
    return new JobDelegator(executionService());
  }

  @Bean // Generic Job
  JobExecutionService executionService() {
    return new JobExecutionService() {

      @Override
      public void executeJob(JobEntity job) {}

      @Override
      public void rollback() {}
    };
  }

  @Bean
  Tasklet executeJobsTasklet() {
    return new ExecuteJobsTasklet(jobDelegator());
  }
}
