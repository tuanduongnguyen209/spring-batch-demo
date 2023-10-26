package duongnt.example.springbatchdemo.config;

import duongnt.example.springbatchdemo.entity.Student;
import duongnt.example.springbatchdemo.repository.StudentRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.support.JdbcTransactionManager;

import java.util.Map;

@Configuration
@EnableBatchProcessing
public class CalculateScoreJobConfiguration {

    @Bean
    @StepScope
    public RepositoryItemReader<Student> itemReader(StudentRepository repository, @Value("#{jobParameters['chunkSize']}" ) Long chunkSize) {
        return new RepositoryItemReaderBuilder<Student>()
                .name("itemReader")
                .pageSize(Math.toIntExact(chunkSize))
                .methodName("findAll")
                .repository(repository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public RepositoryItemWriter<Student> itemWriter(StudentRepository repository) {
        return new RepositoryItemWriterBuilder<Student>()
                .methodName("save")
                .repository(repository)
                .build();
    }

    @Bean
    public ItemProcessor<Student, Student> itemProcessor() {
        return item -> {
            // Calculate average score
            int sum = item.getMathScore() + item.getChemistryScore() + item.getPhysicsScore();
            item.setAverageScore(sum / 3);
            return item;
        };
    }

    @Bean
    @JobScope
    public Step calculateAvgScoreStep(RepositoryItemReader<Student> itemReader,
                                      RepositoryItemWriter<Student> itemWriter,
                                      ItemProcessor<Student, Student> itemProcessor,
                                      JobRepository jobRepository,
                                      JdbcTransactionManager jdbcTransactionManager,
                                      @Value("#{jobParameters['chunkSize']}" ) Long chunkSize
    ) {
        return new StepBuilder("calculateAvgScoreStep", jobRepository)
                .<Student, Student>chunk(Math.toIntExact(chunkSize), jdbcTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job calculateAvgScoreJob(JobRepository jobRepository, Step calculateAvgScoreStep) {
        return new JobBuilder("calculateAvgScoreJob", jobRepository)
                .start(calculateAvgScoreStep)
                .build();
    }
}
