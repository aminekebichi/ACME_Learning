package acme.learning.hbp.student;

import acme.learning.hbp.course.Course;
import acme.learning.hbp.course.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student getStudentById(Long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElse(null);
    }
    public List<Course> getEnrolledCourses(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return student.getEnrolledCourses();
        } else {
            throw new ResourceNotFoundException("Student not found");
        }
    }
    public void dropCourse(Long studentId, Long courseId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalStudent.isPresent() && optionalCourse.isPresent()) {
            Student student = optionalStudent.get();
            Course course = optionalCourse.get();

            student.getEnrolledCourses().remove(course);
            course.getStudents().remove(student);

            // Update the database
            studentRepository.save(student);
            courseRepository.save(course);
        } else {
            // Handle case where student or course is not found
            throw new EntityNotFoundException("Student or Course not found");
        }
    }

}
