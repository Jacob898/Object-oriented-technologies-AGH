package pl.edu.agh.to.school;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.student.Student;
import pl.edu.agh.to.school.student.StudentService;

import java.util.List;

@Service
public class SchoolInitializer {
    //private final Course course;

    @Value("${school.app.version}") String appVersion;

    private final StudentService studentService;
    private final List<Course> courses;
    //@Qualifier("networks") Course course
    public SchoolInitializer( StudentService studentService, List<Course> courses) {
        this.studentService = studentService;
        this.courses = courses;
    }

//    @PostConstruct
//    public void initComputerNetworkCourse() {
//        course.getStudents()
//                .forEach(student -> studentService.assignGrade(student,course, 4.5));
//    }
//
//    @PostConstruct
//    public void initComputerNetworksCourse() {
//        course.getStudents()
//                .forEach(student -> studentService.assignGrade(student, course, 4.5));
//    }

    @PostConstruct
    public void assignGradesToAllStudents() {
        System.out.println("Application version: " + appVersion);
        for (Course course : courses) {
            for (Student student : course.getStudents()) {
                studentService.assignGrade(student, course, 4.5);
            }
        }
    }
}
