package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.service.SchoolService;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;

public class StudentRepository implements Repository<Student>
{
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final GradeDao gradeDao;
    private final TransactionService transactionService;

    public StudentRepository(StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao, TransactionService transactionService) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
        this.transactionService = transactionService;
    }

    @Override
    public Optional<Student> add(Student student) {
        return studentDao.save(student);
    }

    @Override
    public Optional<Student> getById(int id) {
        return studentDao.findByIndexNumber(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
            student.courseSet().forEach(course -> course.studentSet().remove(student));
            student.courseSet().clear();
            studentDao.remove(student);
    }


    public List<Student> findAllByCourseName(String courseName) {
        Course course = courseDao.findByName(courseName).orElse(null);
        if (course == null) {
            return Collections.emptyList();
        } else {
            return List.copyOf(course.studentSet());
        }

    }

}
