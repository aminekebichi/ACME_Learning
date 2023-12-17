package acme.learning.hbp.course;

import acme.learning.hbp.course.Course;
import acme.learning.hbp.course.CourseController;
import acme.learning.hbp.course.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateCourse() throws Exception {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setName("Java Programming");

        Mockito.when(courseService.createCourse(Mockito.any(Course.class))).thenReturn(course);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Java Programming\"}"));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java Programming"));
    }

    @Test
    public void testGetAllCourses() throws Exception {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setName("Java Programming");

        Mockito.when(courseService.getAllCourses()).thenReturn(Collections.singletonList(course));

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/courses"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Java Programming"));
    }

    @Test
    public void testGetCourseById_ExistingCourse() throws Exception {
        // Arrange
        Long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);
        course.setName("Java Programming");

        Mockito.when(courseService.getCourseById(courseId)).thenReturn(course);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", courseId));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId))
                .andExpect(jsonPath("$.name").value("Java Programming"));
    }

    @Test
    public void testGetCourseById_NonExistingCourse() throws Exception {
        // Arrange
        Long nonExistingCourseId = 999L;
        Mockito.when(courseService.getCourseById(nonExistingCourseId)).thenReturn(null);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", nonExistingCourseId));

        // Assert
        result.andExpect(status().isNotFound());
    }
}