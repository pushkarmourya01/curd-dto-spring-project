package com.example.demo.controller;

import com.example.demo.DTO.StudentRequestDTO;
import com.example.demo.DTO.StudentResponseDTO;
import com.example.demo.UPDATE_DTO.updateStudentRequestDTO;
import com.example.demo.UPDATE_DTO.updateStudentResponseDTO;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    public StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create") // yeh create hai na eska mtlb  request kr rha toh request dto mein
    public ResponseEntity<StudentResponseDTO> createStudent // student response dto mein na final result save hogaa
            (@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO createStudent = studentService.createStudent(studentRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createStudent);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StudentResponseDTO> getStud(@PathVariable Long id) {
        StudentResponseDTO studentResp = studentService.getStud(id);

        if (studentResp == null) {
            return status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
        .status(HttpStatus.OK)
                .body(studentResp);
    }
        @GetMapping("/getAll")
        public ResponseEntity<List<StudentResponseDTO>> getAllStudent () {
            List<StudentResponseDTO> studentList = studentService.getAllStudent();
            if (studentList == null) {
                return status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            return status(HttpStatus.OK)
                    .body(studentList);
        }
        @PutMapping("/update/{id}")
        public ResponseEntity<updateStudentResponseDTO> studentUpdate (@PathVariable Long id, @RequestBody updateStudentRequestDTO updateStudentRequestDTO){
            updateStudentResponseDTO studentResp = studentService.updateStudent(id, updateStudentRequestDTO); // left side wala final result haina woh wohi save hoga response mein na
            if (studentResp == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(studentResp);
        }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        boolean isDeleted = studentService.deleteStudent(id);

        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Record Deleted");
    }
}