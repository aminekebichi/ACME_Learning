package acme.learning.hbp.course;

import acme.learning.hbp.exceptions.CourseAlreadyAddedException;
import acme.learning.hbp.exceptions.CourseAlreadyStartedException;
import acme.learning.hbp.instructor.Instructor;
import acme.learning.hbp.instructor.InstructorRepository;
import acme.learning.hbp.student.Student;
import acme.learning.hbp.student.StudentRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    StudentRepository studentRepository;

    public Course createCourse(Course course){
        return courseRepository.save(course);
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course getCourseById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.orElse(null);
    }
    public Course assignInstructorToCourse(Long courseId, Long instructorId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);

        if (optionalCourse.isPresent() && optionalInstructor.isPresent()) {
            Course course = optionalCourse.get();
            Instructor instructor = optionalInstructor.get();

            course.setInstructor(instructor);
            return courseRepository.save(course);
        } else {
            throw new ResourceNotFoundException("Course or Instructor not found");
        }
    }
    public Course setCourseToStarted(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.startCourse();
            return courseRepository.save(course);
        } else {
            throw new ResourceNotFoundException("Course not found");
        }
    }
    public Course assignStudentToCourse(Long courseId, Long studentId) throws CourseAlreadyAddedException {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalCourse.isPresent() && optionalStudent.isPresent()) {
            Course course = optionalCourse.get();
            Student student = optionalStudent.get();
            // Check if the course has not started
            if (!course.isStarted()) {
                //Check if student is not already registered
                if (!student.getEnrolledCourses().contains(course)) {
                    List<Course> studentEnrolledCourses = student.getEnrolledCourses();
                    List<Student> enrolledStudents = course.getStudents();

                    studentEnrolledCourses.add(course);
                    enrolledStudents.add(student);

                    student.setEnrolledCourses(studentEnrolledCourses);
//                    course.setStudents(enrolledStudents);

                    courseRepository.save(course);
                    studentRepository.save(student);
                } else {
                    throw new CourseAlreadyAddedException("Cannot enroll in a class you are already registered in");
                }

                return course;
            } else {
                throw new CourseAlreadyStartedException("Cannot assign student to a started course");
            }
        } else {
            throw new ResourceNotFoundException("Course or Student not found");
        }
    }
}
