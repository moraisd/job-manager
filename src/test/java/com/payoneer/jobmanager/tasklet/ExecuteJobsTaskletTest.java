package com.payoneer.jobmanager.tasklet;

import com.payoneer.jobmanager.execution.JobDelegator;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
class ExecuteJobsTaskletTest {


  @Mock private JobDelegator delegator;

  @Mock private StepContribution stepContribution;

  @Mock private ChunkContext chunkContext;

  @InjectMocks private ExecuteJobsTasklet tasklet;

  @Test
  void shouldExecuteTasklet() throws Exception {
    val result = tasklet.execute(stepContribution, chunkContext);
    assertThat(result, is(RepeatStatus.FINISHED));
  }
}
