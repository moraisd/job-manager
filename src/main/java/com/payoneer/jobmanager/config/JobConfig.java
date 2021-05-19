package com.payoneer.jobmanager.config;

import com.payoneer.jobmanager.domain.job.JobEntity;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ConfigurationProperties
public class JobConfig {
  private List<JobEntity> job;

  public List<JobEntity> getJobsListByPriority() {
    return job.stream()
        .sorted(Comparator.comparing(JobEntity::getPriority).reversed())
        .collect(Collectors.toList());
  }
}
