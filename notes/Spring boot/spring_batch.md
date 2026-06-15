# Spring Batch

## 1. Have you worked with Spring Batch?

Yes, Spring Batch is used for processing large volumes of records offline in chunks. It is designed around the **Job** and **Step** pattern:

### 1. Core Architecture
- **Job:** A complete batch process containing one or more Steps.
- **Step:** A sequential phase of a Job, typically structured around **Reader-Processor-Writer** chunk processing:
  - `ItemReader`: Reads data from a source (DB, CSV, XML, JSON).
  - `ItemProcessor`: Applies business logic/transformation to the read item.
  - `ItemWriter`: Writes a chunk of processed items to the target (DB, Kafka, File).
- **JobRepository:** Persists job execution metadata (status, parameters, failures) in a database.
- **JobLauncher:** Starts the job execution.

### 2. Coding Example (Chunk Configuration):
```java
@Configuration
public class BatchConfig {
    
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("csv-import-step", jobRepository)
            .<UserCsv, UserEntity>chunk(1000, transactionManager) // Chunk size 1000
            .reader(csvReader())
            .processor(userProcessor())
            .writer(jpaWriter())
            .build();
    }
    
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
            .start(step1)
            .build();
    }
}
```

### 3. Real-world Use Case:
- **Transaction Processing:** Reading 1 million transaction CSV files daily, transforming/cleaning the data (formatting dates, validations), and saving them to a PostgreSQL database in chunks of 1000 records to prevent memory exhaustion.
