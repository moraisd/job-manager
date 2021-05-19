package com.payoneer.jobmanager.execution;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JobScheduler {

  private static Logger logger = Logger.getLogger("com.payoneer.jobmanager.execution.JobScheduler");
  private final Job jobManager;
  private final JobLauncher jobLauncher;

  @Scheduled(cron = "${jobs.schedule}")
  void scheduleJobExecution() throws Exception {
    logger.info("Starting scheduled task");
    jobLauncher.run(jobManager, new JobParameters());
    logger.info("Ending scheduled task");
  }
}
