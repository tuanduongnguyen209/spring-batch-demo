package duongnt.example.springbatchdemo.service;

import duongnt.example.springbatchdemo.entity.Student;
import duongnt.example.springbatchdemo.repository.StudentRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final JobLauncher jobLauncher;
    private final Job job;
    @Autowired
    public StudentService(StudentRepository studentRepository, JobLauncher jobLauncher, Job job) {
        this.studentRepository = studentRepository;
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public void deleteAll() {
        studentRepository.deleteAll();
    }
    public void createStudent(int total) {
        var students = new ArrayList<Student>();
        for (int i = 1; i <= total; i++) {
            int mathScore = (int) (Math.random() * 10);
            int physicsScore = (int) (Math.random() * 10);
            int chemistryScore = (int) (Math.random() * 10);

            students.add(new Student(i, "Student " + i, mathScore, physicsScore, chemistryScore));
        }
        studentRepository.saveAll(students);
    }

    public void startCalculateAvgScore(int chunkSize) throws Exception {
        var jobParameters = new JobParametersBuilder()
                .addLong("chunkSize", (long) chunkSize)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }

    public int countStudent() {
        return (int) studentRepository.count();
    }
}
