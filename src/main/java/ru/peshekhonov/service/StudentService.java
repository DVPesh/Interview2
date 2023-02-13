package ru.peshekhonov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.peshekhonov.entity.Student;
import ru.peshekhonov.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Student save(Student student) {
        return repository.save(student);
    }

    public boolean doesStudentExist(Student student) {
        Long id = student.getId();
        if (id != null) {
            return repository.existsById(id);
        }
        return false;
    }
}
