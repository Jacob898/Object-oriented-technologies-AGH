package pl.edu.agh.to.school.notification;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.student.Student;

@Profile("test")

@Service
public class ConsoleNotificationService implements NotificationService {
    public ConsoleNotificationService() {

    }

    @PostConstruct
    void onStarted() {
        System.out.println("Console notification service is up");
    }

    @PreDestroy
    void onCleanup() {
        System.out.println("Console notification service is down");
    }

    @Override
    public void notify(Student student, Grade grade) {
        System.out.println(student.getFirstName() + " " + student.getLastName() + " otrzymal ocene o wartosci " + grade.toString());
    }
}
