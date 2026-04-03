package pl.edu.agh.iisg.to.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.SessionService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentDao extends GenericDao<Student> {

    public StudentDao(SessionService sessionService) {
        super(sessionService, Student.class);
    }

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        Student student = new Student(firstName, lastName, indexNumber);
        return save(student);

    }

    public List<Student> findAll() {
        Session session = super.currentSession();
        List<Student> students = session.createQuery("SELECT s FROM Student s ORDER BY s.lastName").list();
        return students;
    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
        Session session = super.currentSession();
        Optional<Student> result = Optional.empty();
        try {
            Student student = session.createQuery(
                            "SELECT s FROM Student s WHERE s.indexNumber = :indexNumber", Student.class)
                                .setParameter("indexNumber", indexNumber)
                                .getSingleResult();
            result = Optional.of(student);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return result;
    }
}
