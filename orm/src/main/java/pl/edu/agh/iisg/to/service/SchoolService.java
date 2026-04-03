package pl.edu.agh.iisg.to.service;

import org.hibernate.Transaction;
import org.hibernate.Session;
import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.repository.StudentRepository;
import pl.edu.agh.iisg.to.session.SessionService;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;

//public class SchoolService {
//
//    private final TransactionService transactionService;
//
//    private final StudentDao studentDao;
//
//    private final CourseDao courseDao;
//
//    private final GradeDao gradeDao;
//
//    public SchoolService(TransactionService transactionService, StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
//        this.transactionService = transactionService;
//        this.studentDao = studentDao;
//        this.courseDao = courseDao;
//        this.gradeDao = gradeDao;
//    }
//
//
//
//    public boolean enrollStudent(final Course course, final Student student) {
//        return transactionService.doAsTransaction(() -> {
//            if (course.studentSet().contains(student)) {
//                return false;
//            }
//            course.studentSet().add(student);
//            student.courseSet().add(course);
//            courseDao.save(course);
//            studentDao.save(student);
//            return true;
//        }).orElse(false);
//    }
//
//
//    public boolean removeStudent(int indexNumber) {
//        return transactionService.doAsTransaction(() -> {
//            Student student = studentDao.findByIndexNumber(indexNumber).orElseThrow();
//            for (Course course : student.courseSet()) {
//                course.studentSet().remove(student);
//                courseDao.save(course);
//            }
//            studentDao.remove(student);
//            return true;
//        }).orElse(false);
//    }
//
//    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
//        return transactionService.doAsTransaction(() -> {
//            if (!course.studentSet().contains(student)) {
//                course.studentSet().add(student);
//                student.courseSet().add(course);
//            }
//            Grade grade = new Grade(student, course, gradeValue);
//
//            student.gradeSet().add(grade);
//            course.gradeSet().add(grade);
//
//
//            studentDao.save(student);
//            courseDao.save(course);
//            gradeDao.save(grade);
//            return true;
//        }).orElse(false);
//    }
//
//    public Map<String, List<Float>> getStudentGrades(String courseName) {
//        return transactionService.doAsTransaction(() -> {
//            Course course = courseDao.findByName(courseName).orElseThrow();
//            Map<String, List<Float>> report = new java.util.HashMap<>();
//            for (Student student : course.studentSet()) {
//                List<Float> grades = student.gradeSet().stream()
//                        .filter(grade -> grade.course().equals(course))
//                        .map(grade -> grade.grade())
//                        .sorted()
//                        .toList();
//                report.put(student.fullName(), grades);
//            }
//            return report;
//        }).orElse(Collections.emptyMap());
//    }
//}



// ------------Zadanie 2------------------
public class SchoolService {

    private final TransactionService transactionService;

    private final StudentRepository studentRepository;

    private final GradeDao gradeDao;

    public SchoolService(TransactionService transactionService, StudentRepository studentRepository, GradeDao gradeDao) {
        this.transactionService = transactionService;
        this.studentRepository = studentRepository;
        this.gradeDao = gradeDao;
    }



    public boolean enrollStudent(final Course course, final Student student) {
        return transactionService.doAsTransaction(() -> {
            if (course.studentSet().contains(student)) {
                return false;
            }
            course.studentSet().add(student);
            student.courseSet().add(course);
            studentRepository.add(student);
            return true;
        }).orElse(false);
    }


    public boolean removeStudent(int indexNumber) {
        return transactionService.doAsTransaction(() -> {
            Student student = studentRepository.getById(indexNumber).orElseThrow();
            for (Course course : student.courseSet()) {
                course.studentSet().remove(student);
            }
            studentRepository.remove(student);
            return true;
        }).orElse(false);
    }


    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
        return transactionService.doAsTransaction(() -> {
            if (!course.studentSet().contains(student)) {
                course.studentSet().add(student);
                student.courseSet().add(course);
            }
            Grade grade = new Grade(student, course, gradeValue);

            student.gradeSet().add(grade);
            course.gradeSet().add(grade);

            gradeDao.save(grade);
            studentRepository.add(student);
            return true;
        }).orElse(false);
    }


    public Map<String, List<Float>> getStudentGrades(String courseName) {
        return transactionService.doAsTransaction(() -> {
            List<Student> students = studentRepository.findAllByCourseName(courseName);
            if (students.isEmpty()) {
                return Collections.<String, List<Float>>emptyMap();
            }

            Map<String, List<Float>> report = new HashMap<>();

            for (Student student : students) {
                List<Float> grades = student.gradeSet().stream()
                        .filter(grade -> grade.course().name().equals(courseName))
                        .map(grade -> grade.grade())
                        .sorted()
                        .toList();
                report.put(student.fullName(), grades);
            }

            return report;
        }).orElse(Collections.emptyMap());
    }


}
