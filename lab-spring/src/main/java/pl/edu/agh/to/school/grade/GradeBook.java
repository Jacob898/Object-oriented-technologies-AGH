package pl.edu.agh.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.student.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeBook {

    public GradeBook() {

    }

    @PostConstruct
    void onStarted() {
        System.out.println("Grade book is up");
    }

    public Grade assignGrade(Student student, Course course, double gradeValue) {
        var grade = new Grade(course, gradeValue);

        String indexNumber = student.getIndexNumber();

        studentGrades
                .computeIfAbsent(indexNumber, k-> new ArrayList<>())
                .add(grade);
        return grade;
    }


    private final Map<String, List<Grade>> studentGrades = new HashMap<>();

    public Map<String, List<Grade>> getStudentGrades() {
        return studentGrades;
    }

    @PreDestroy
    void onCleanup() {
        System.out.println("Grade book is down");
    }

}
