package acme.learning.hbp.course;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import acme.learning.hbp.student.Student;
import acme.learning.hbp.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void testAssignStudentToCourse() {
        // Create a sample course
        Course sampleCourse = new Course();
        sampleCourse.setId(1L);
        sampleCourse.setName("Sample Course");

        // Create a sample student
        Student sampleStudent = new Student();
        sampleStudent.setId(1L);
        sampleStudent.setName("Sample Student");

        // Mock the behavior of findById method for both course and student
        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        // Call the service method
        Course assignedCourse = courseService.assignStudentToCourse(1L, 1L);

        // Assert that the student is assigned to the course
        assertTrue(assignedCourse.getStudents().contains(sampleStudent));
        assertTrue(sampleStudent.getEnrolledCourses().contains(assignedCourse));
    }
}
