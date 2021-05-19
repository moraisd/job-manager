package com.payoneer.jobmanager.domain.job;

import lombok.Data;

import java.io.Serializable;

@Data
public class JobEntity implements Serializable {
  private JobType type;
  private JobState state = JobState.QUEUED;
  private JobPriority priority = JobPriority.NORMAL;
}
