package xyz.stodo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.stodo.entity.Semester;
import xyz.stodo.entity.User;
import xyz.stodo.exception.InvalidFileException;
import xyz.stodo.file.FileManager;
import xyz.stodo.repository.SemesterRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class ShareService {
    @Autowired
    private FileManager fileManager;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SemesterService semesterService;

    public void load(MultipartFile file, User user) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = fileManager.parseFileToString(file);
            Semester semester = mapper.readValue(s, Semester.class);
            semester.setUser(user);
            semesterRepository.save(semester);
        }
        catch (IOException e) {
            throw new InvalidFileException("File is invalid");
        }
    }

    public String dump(Long semesterId, User user) throws JsonProcessingException {
        Semester semester = semesterService.getSemesterByIdAndUser(semesterId, user);
        String json = new ObjectMapper().writeValueAsString(semester);
        return json;
    }
}
