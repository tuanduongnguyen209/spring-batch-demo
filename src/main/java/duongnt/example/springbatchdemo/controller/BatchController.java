package duongnt.example.springbatchdemo.controller;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class BatchController {
    @Autowired
    private JobExplorer jobExplorer;

    @GetMapping("/runningJobs")
    public List<JobExecution> getJobInstances() {

        var instances = jobExplorer.getLastJobInstance("calculateScoreJob");
        if (instances == null) {
            return null;
        }
        return jobExplorer.getJobExecutions(instances);
    }
}
