package com.payoneer.jobmanager.integration;

import com.payoneer.jobmanager.config.BatchConfig;
import com.payoneer.jobmanager.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@SpringBatchTest
@ContextConfiguration(classes = {BatchConfig.class, TestConfig.class})
@ActiveProfiles("test")
class JobManagerApplicationTests {

  @Autowired private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  void runApplication() throws Exception {
    jobLauncherTestUtils.launchJob();
  }
}
