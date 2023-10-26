package duongnt.example.springbatchdemo.controller;

import duongnt.example.springbatchdemo.service.StudentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @DeleteMapping("/students")
    public void deleteAll() {
        studentService.deleteAll();
    }

    @PostMapping("/students")
    public void createStudent(@RequestParam int total) {
        studentService.createStudent(total);
    }

    @PostMapping("/students/calculateAvgScore")
    public void startCalculateAvgScore(@RequestParam int chunkSize) throws Exception {
        studentService.startCalculateAvgScore(chunkSize);
    }
}
