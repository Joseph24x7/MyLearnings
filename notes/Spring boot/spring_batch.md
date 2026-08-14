# Spring Batch

## 1. Have you worked with Spring Batch?

Yes, Spring Batch is used for processing large volumes of records offline in chunks (ETL pipelines, DB report generation, CSV imports, scheduled end-of-day batch jobs). It is designed around the **Job** and **Step** pattern:

### 1. Architecture & Core Concepts
```
Job → Step → [ItemReader → ItemProcessor → ItemWriter]
```
- **Job:** A complete batch process containing one or more Steps.
- **Step:** A sequential phase of a Job, structured around **Reader-Processor-Writer** chunk processing:
  - `ItemReader`: Reads data from a source (DB, CSV, XML, JSON).
  - `ItemProcessor`: Applies business logic/transformation to the read item.
  - `ItemWriter`: Writes a chunk of processed items to the target (DB, Kafka, File).
- **JobRepository:** Persists job execution metadata in DB tables (`BATCH_JOB_INSTANCE`, `BATCH_JOB_EXECUTION`).
- **JobLauncher:** Starts the job execution.

### 2. Key Annotations
- `@EnableBatchProcessing`: Enables Spring Batch features and auto-configures `JobRepository` & `JobLauncher`.
- `@JobScope` / `@StepScope`: Late-binds bean creation to the job/step execution context (useful for dynamic step parameters).

### 3. Chunk Configuration & Reader Example
```java
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    
    @Bean
    public Step csvImportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("csv-import-step", jobRepository)
            .<UserCsv, UserEntity>chunk(1000, transactionManager) // Process 1000 records per transaction chunk
            .reader(userCsvReader())
            .processor(userProcessor())
            .writer(jpaWriter())
            .build();
    }
    
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step csvImportStep) {
        return new JobBuilder("importUserJob", jobRepository)
            .start(csvImportStep)
            .build();
    }

    @Bean
    public FlatFileItemReader<UserCsv> userCsvReader() {
        return new FlatFileItemReaderBuilder<UserCsv>()
            .name("userCsvReader")
            .resource(new ClassPathResource("users.csv"))
            .delimited()
            .names("name", "email")
            .targetType(UserCsv.class)
            .build();
    }
}
```

### 4. Restartability & Skip Logic
Spring Batch tracks execution state in its metadata tables. If a job fails midway (e.g. at record 450,000 of 1,000,000), it can be **restarted** from the last successfully committed chunk rather than starting over from zero.

