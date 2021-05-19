package com.payoneer.jobmanager.execution;

import com.payoneer.jobmanager.domain.job.JobEntity;

public interface JobExecutionService {

  void executeJob(JobEntity job);

  void rollback();
}
