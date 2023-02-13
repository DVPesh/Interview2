package ru.peshekhonov.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDto {

    private Long id;
    private String name;
    private int age;
}
