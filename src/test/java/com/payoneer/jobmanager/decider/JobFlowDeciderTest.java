package com.payoneer.jobmanager.decider;

import com.payoneer.jobmanager.config.JobConfig;
import com.payoneer.jobmanager.domain.job.JobEntity;
import com.payoneer.jobmanager.domain.job.JobPriority;
import com.payoneer.jobmanager.domain.job.JobType;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.test.MetaDataInstanceFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobFlowDeciderTest {

  @Mock private JobConfig jobConfig;

  @InjectMocks private JobFlowDecider decider;

  @Test
  void shouldContinueExecution() {
    when(jobConfig.getJob()).thenReturn(generateJobsConfigList());

    val jobExecution = generateJobExecution();
    decider.beforeJob(jobExecution);

    val result = decider.decide(jobExecution, null);
    assertThat(result, equalTo(new FlowExecutionStatus("CONTINUE")));
  }

  @Test
  void shouldCompleteExecution() {
    val jobExecution = generateJobExecution();

    val result = decider.decide(jobExecution, null);
    assertThat(result, is(FlowExecutionStatus.COMPLETED));
  }

  private JobExecution generateJobExecution() {
    return MetaDataInstanceFactory.createJobExecution();
  }

  private List<JobEntity> generateJobsConfigList() {
    val job1 = new JobEntity();
    job1.setType(JobType.ADDITION);

    val job2 = new JobEntity();
    job2.setPriority(JobPriority.HIGH);
    job2.setType(JobType.SUBTRACTION);

    return List.of(job1, job2);
  }
}
