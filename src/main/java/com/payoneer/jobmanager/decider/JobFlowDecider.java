package com.payoneer.jobmanager.decider;

import com.payoneer.jobmanager.config.JobConfig;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.List;

@RequiredArgsConstructor
public class JobFlowDecider implements JobExecutionDecider {

  public static final String JOBS_CONTEXT = "jobsContext";
  private final JobConfig jobConfig;

  @BeforeJob
  void beforeJob(JobExecution jobExecution) {
    insertPrioritySortedJobsIntoContext(jobExecution);
  }

  @Override
  public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
    val jobs = (List) jobExecution.getExecutionContext().get(JOBS_CONTEXT);

    if (jobs != null && !jobs.isEmpty()) {
      return new FlowExecutionStatus("CONTINUE");
    }
    return FlowExecutionStatus.COMPLETED;
  }

  private void insertPrioritySortedJobsIntoContext(JobExecution jobExecution) {
    jobExecution.getExecutionContext().put(JOBS_CONTEXT, jobConfig.getJobsListByPriority());
  }
}
