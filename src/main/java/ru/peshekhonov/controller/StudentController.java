package ru.peshekhonov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.peshekhonov.dto.StudentDto;
import ru.peshekhonov.entity.Student;
import ru.peshekhonov.exception.AppError;
import ru.peshekhonov.exception.ResourceAlreadyExistsException;
import ru.peshekhonov.exception.ResourceNotFoundException;
import ru.peshekhonov.mapper.StudentDtoMapper;
import ru.peshekhonov.service.StudentService;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;
    private final StudentDtoMapper mapper;

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return mapper.map(service.findAll());
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Студент с id=" + id + " не найден"));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudent(@RequestBody Student student) {
        if (service.doesStudentExist(student)) {
            throw new ResourceAlreadyExistsException("Студент c id=" + student.getId() + " уже существует");
        }
        service.save(student);
    }

    @PutMapping
    public void updateStudent(@RequestBody Student student) {
        if (!service.doesStudentExist(student)) {
            throw new ResourceNotFoundException("Студент с id=" + student.getId() + " не найден");
        }
        service.save(student);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleException(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                AppError.builder()
                        .code("RESOURCE_NOT_FOUND")
                        .error(e.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleException(ResourceAlreadyExistsException e) {
        return new ResponseEntity<>(
                AppError.builder()
                        .code("BAD_REQUEST")
                        .error(e.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleException(DateTimeParseException e) {
        return new ResponseEntity<>(
                AppError.builder()
                        .code("BAD_REQUEST")
                        .error("Не верный формат даты")
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(
                AppError.builder()
                        .code("BAD_REQUEST")
                        .error("Введены не все данные или не в том формате")
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(
                AppError.builder()
                        .code("BAD_REQUEST")
                        .error("Введены не все данные или не в том формате")
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(
                AppError.builder()
                        .code("BAD_REQUEST")
                        .error("Введены не все данные или не в том формате")
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
