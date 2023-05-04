package ru.scrin.service.controlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.scrin.service.services.Screenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("/api")
@Controller
public class UserController {
    @Autowired
    private Screenshot screenshot;



    @GetMapping("/screenshot")
    public ResponseEntity<Resource> getUserPhoto() throws IOException {
        screenshot.makeSreen();
        Path path = Paths.get("src/screenshot/scr.jpg");
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(data.length)
                .body(resource);
    }
}