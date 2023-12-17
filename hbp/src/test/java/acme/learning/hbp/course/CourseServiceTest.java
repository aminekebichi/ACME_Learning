package acme.learning.hbp.course;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
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

    @Test
    void testGetStudentsByCourse() {
        // Create a sample course
        Course sampleCourse = new Course();
        sampleCourse.setId(1L);
        sampleCourse.setName("Sample Course");

        // Create sample students
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Student 1");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Student 2");

        // Assign students to the sample course
        sampleCourse.setStudents(Arrays.asList(student1, student2));

        // Mock the behavior of findById method
        when(courseRepository.findById(1L)).thenReturn(Optional.of(sampleCourse));

        // Call the service method
        List<Student> students = courseService.getStudentsByCourse(1L);

        // Assert the result
        assertNotNull(students, "The list of students should not be null");
        assertEquals(2, students.size(), "The number of students should be 2");
        assertTrue(students.contains(student1), "Student 1 should be in the list");
        assertTrue(students.contains(student2), "Student 2 should be in the list");
    }
}
