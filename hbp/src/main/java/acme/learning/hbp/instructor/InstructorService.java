package acme.learning.hbp.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }
    public Instructor getInstructorById(Long id) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        return optionalInstructor.orElse(null);
    }
}
