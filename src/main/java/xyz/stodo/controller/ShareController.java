package xyz.stodo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.stodo.entity.User;
import xyz.stodo.exception.InvalidFileException;
import xyz.stodo.file.FileManager;
import xyz.stodo.repository.SemesterRepository;
import xyz.stodo.service.RegistrationService;
import xyz.stodo.service.ShareService;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/share")
@CrossOrigin
public class ShareController {

    @Autowired
    private FileManager fileManager;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ShareService shareService;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void load(@RequestParam("file") MultipartFile file,
                     Principal principal) {
        User user = registrationService.getUserByPrincipal(principal);
        shareService.load(file, user);
    }

    @RequestMapping(value = "/dump/{semesterId}", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> dump(@PathVariable String semesterId,
                                                  Principal principal) throws JsonProcessingException {
        User user = registrationService.getUserByPrincipal(principal);

        String fileName = fileManager.getFileName();
        MediaType mediaType =
                fileManager.getMediaTypeForFileName(this.servletContext, fileName);
        byte[] data = shareService.dump(Long.parseLong(semesterId), user).getBytes();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + fileManager.getFileName())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(resource);
    }

}
