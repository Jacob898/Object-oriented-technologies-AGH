package pl.edu.agh.to.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.grade.GradeBook;
import pl.edu.agh.to.school.notification.NotificationService;
import pl.edu.agh.to.school.student.Student;
import pl.edu.agh.to.school.student.StudentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class LabSpringApplicationTests {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeBook gradeBook;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void testAssignGradeToStudent() {

        Student student = new Student("Robert", "Robertson", LocalDate.of(2013, 2, 7), "123456", "robbie@example.com");

        Course course = new Course("OOP");

        studentService.assignGrade(student, course, 4.0);

        Map<String, List<Grade>> studentGrades = gradeBook.getStudentGrades();

        assertThat(studentGrades).containsKey(student.getIndexNumber());
        List<Grade> grades = studentGrades.get(student.getIndexNumber());
        assertThat(grades).anyMatch(grade -> grade.value() == 4.0 && grade.course().getName().equals("OOP"));
    }

}
