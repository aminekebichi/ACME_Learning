package acme.learning.hbp.student;

import acme.learning.hbp.student.Student;
import acme.learning.hbp.student.StudentController;
import acme.learning.hbp.student.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @MockBean
    private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateStudent() throws Exception {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");

        Mockito.when(studentService.createStudent(Mockito.any(Student.class))).thenReturn(student);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");

        Mockito.when(studentService.getAllStudents()).thenReturn(Collections.singletonList(student));

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/students"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setName("John Doe");

        Mockito.when(studentService.getStudentById(studentId)).thenReturn(student);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", studentId));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }
}