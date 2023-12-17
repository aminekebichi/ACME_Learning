package acme.learning.hbp.course;

import acme.learning.hbp.instructor.Instructor;
import acme.learning.hbp.instructor.InstructorRepository;
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
}
