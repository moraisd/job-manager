# job-manager
Payoneer project

Job Management System

Current implementation supports running jobs based on application.properties configuration settings, where:

job.type      - Type of action the job will perform
job.priority  - 3 settings of priority (Low, Medium, High)
jobs.schedule - Cron-type configuration to run all jobs


Existing implementation problems:

1 - No sample jobs given, so no full integration tests
2 - Jobs are run on app start regardless of schedule settings. Scheduled jobs shouldn't trigger job execution during app startup
3 - Job-Independent schedule implementation missing

Open points:

Flexibility - "The types of possible actions performed by the Jobs are not known to the Job
Management System. In the future, new Jobs should be supported without re-developing
the Job Management System (optional)."

New jobs means new jobs doing existing actions or completely new actions?

How to perform an action without an implementation?


Total time spent: ~12h
