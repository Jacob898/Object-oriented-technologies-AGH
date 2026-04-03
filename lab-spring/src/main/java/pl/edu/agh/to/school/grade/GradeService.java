package pl.edu.agh.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.notification.NotificationService;
import pl.edu.agh.to.school.student.Student;

@Service
public class GradeService {

    private final GradeBook gradeBook;
    private final NotificationService notificationService;

    public GradeService(GradeBook gradeBook, NotificationService notificationService) {
        this.gradeBook = gradeBook;
        this.notificationService = notificationService;
    }

    public void assignGrade(Student student, Course course, double gradeValue) {
        Grade grade = gradeBook.assignGrade(student, course, gradeValue);
        notificationService.notify(student,grade);
    }


    @PostConstruct
    void onStarted() {
        System.out.println("GradeService is up");
    }



    @PreDestroy
    void onCleanup() {
        System.out.println("GradeService is cleaned");
    }
}
