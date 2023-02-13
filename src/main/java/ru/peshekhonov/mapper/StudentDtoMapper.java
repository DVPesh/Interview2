package ru.peshekhonov.mapper;

import org.mapstruct.Mapper;
import ru.peshekhonov.dto.StudentDto;
import ru.peshekhonov.entity.Student;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentDtoMapper {

    StudentDto map(Student student);

    List<StudentDto> map(List<Student> students);
}
