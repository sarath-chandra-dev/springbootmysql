package com.example.testdemo;



import com.example.testdemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/Students")
public class StudentsController {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentsController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity createStudent(@RequestBody Student Student) throws URISyntaxException {
        Student savedStudent = studentRepository.save(Student);
        return ResponseEntity.created(new URI("/Students/" + savedStudent.getId())).body(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(@PathVariable Long id, @RequestBody Student Student) {
        Student currentStudent = studentRepository.findById(id).orElseThrow(RuntimeException::new);
        currentStudent.setName(Student.getName());
        currentStudent.setEmail(Student.getEmail());
        currentStudent = studentRepository.save(Student);

        return ResponseEntity.ok(currentStudent);
    }


}

