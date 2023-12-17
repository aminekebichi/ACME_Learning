package acme.learning.hbp.instructor;

import acme.learning.hbp.instructor.Instructor;
import acme.learning.hbp.instructor.InstructorController;
import acme.learning.hbp.instructor.InstructorService;
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
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstructorController.class)
public class InstructorControllerTest {

    @MockBean
    private InstructorService instructorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateInstructor() throws Exception {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setId(1L);
        instructor.setName("John Doe");

        Mockito.when(instructorService.createInstructor(Mockito.any(Instructor.class))).thenReturn(instructor);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/instructors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\"}"));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetAllInstructors() throws Exception {
        // Arrange
        Instructor instructor = new Instructor();
        instructor.setId(1L);
        instructor.setName("John Doe");

        Mockito.when(instructorService.getAllInstructors()).thenReturn(Collections.singletonList(instructor));

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/instructors"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testGetInstructorById_ExistingInstructor() throws Exception {
        // Arrange
        Long instructorId = 1L;
        Instructor instructor = new Instructor();
        instructor.setId(instructorId);
        instructor.setName("John Doe");

        Mockito.when(instructorService.getInstructorById(instructorId)).thenReturn(instructor);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/instructors/{id}", instructorId));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(instructorId))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetInstructorById_NonExistingInstructor() throws Exception {
        // Arrange
        Long nonExistingInstructorId = 999L;
        Mockito.when(instructorService.getInstructorById(nonExistingInstructorId)).thenReturn(null);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/instructors/{id}", nonExistingInstructorId));

        // Assert
        result.andExpect(status().isNotFound());
    }
}
