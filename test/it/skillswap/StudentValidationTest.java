package it.skillswap;

import it.skillswap.domain.Student;

public class StudentValidationTest {

    public static void main(String[] args) {

        Student student =
                new Student(
                        "S1",
                        "Anna",
                        "4A",
                        "anna@test.it"
                );

        assert student.getId().equals("S1");
        assert student.getName().equals("Anna");
        assert student.getRatingAvg() == 0.0;

        System.out.println("StudentValidationTest passed!");
    }
}