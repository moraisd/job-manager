package com.payoneer.jobmanager.execution;

import com.payoneer.jobmanager.domain.job.JobEntity;
import lombok.AllArgsConstructor;

import static com.payoneer.jobmanager.domain.job.JobState.*;

@AllArgsConstructor
public class JobDelegator {

  private JobExecutionService executionService;

  public void delegateExecution(JobEntity job) throws Exception {
    try {
      job.setState(RUNNING);
      executionService.executeJob(job);
      job.setState(SUCCESS);
    } catch (Exception e) {
      job.setState(FAILED);
      executionService.rollback();
      throw new Exception(e);
    }
  }
}
