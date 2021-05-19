package com.payoneer.jobmanager.tasklet;

import com.payoneer.jobmanager.domain.job.JobEntity;
import com.payoneer.jobmanager.execution.JobDelegator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

import static com.payoneer.jobmanager.decider.JobFlowDecider.JOBS_CONTEXT;

@RequiredArgsConstructor
public class ExecuteJobsTasklet implements Tasklet {

  private final JobDelegator delegator;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {

    @SuppressWarnings("unchecked")
    val jobs =
        (List<JobEntity>)
            contribution
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .get(JOBS_CONTEXT);

    delegator.delegateExecution(jobs.remove(0));
    return RepeatStatus.FINISHED;
  }
}
