package acme.learning.hbp.course;

import acme.learning.hbp.exceptions.CourseAlreadyStartedException;
import acme.learning.hbp.student.Student;
import jakarta.persistence.EntityNotFoundException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course){
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(){
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Course> getInstructorById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/{courseId}/assign-instructor/{instructorId}")
    public ResponseEntity<Course> assignInstructorToCourse(@PathVariable Long courseId, @PathVariable Long instructorId) {
        Course assignedCourse = courseService.assignInstructorToCourse(courseId, instructorId);
        return new ResponseEntity<>(assignedCourse, HttpStatus.OK);
    }
    @PostMapping("/{courseId}/start-course")
    public ResponseEntity<Course> setCourseToStarted(@PathVariable Long courseId) {
        Course startedCourse = courseService.setCourseToStarted(courseId);
        return new ResponseEntity<>(startedCourse, HttpStatus.OK);
    }
    @PostMapping("/{courseId}/assign-student/{studentId}")
    public ResponseEntity<Object> assignStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        try {
            Course assignedCourse = courseService.assignStudentToCourse(courseId, studentId);
            return new ResponseEntity<>(assignedCourse, HttpStatus.OK);
        } catch (CourseAlreadyStartedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable Long courseId) {
        List<Student> students = courseService.getStudentsByCourse(courseId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
