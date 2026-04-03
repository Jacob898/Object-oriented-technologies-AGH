package pl.edu.agh.to.school.grade;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Grade {

    @Id
    @GeneratedValue
    private int id;

    private int gradeValue;

    public Grade(int value) {
        this.gradeValue = value;
    }

    public Grade() {
    }

    public int getId() {
        return id;
    }

    public int getGradeValue() {
        return gradeValue;
    }
}
