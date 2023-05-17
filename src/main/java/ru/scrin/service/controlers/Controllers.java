package ru.scrin.service.controlers;


import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.scrin.service.services.DBManager;
import ru.scrin.service.services.JwtUtil;
import ru.scrin.service.services.PasswordHasher;
import ru.scrin.service.services.Screenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
@RequestMapping("/api")
@Controller
public class Controllers {
    @Autowired
    private Screenshot screenshot;
    @Autowired
    private DBManager dbManager;



    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return new ResponseEntity<>("{\"message\": \"Missing parameters\"}", HttpStatus.BAD_REQUEST);
        }
        try {


            UsersEntity db_user = new UsersEntity();
            String hash_password = PasswordHasher.hashPassword(user.getPassword());
            db_user.setHashPassword(hash_password);
            db_user.setUsername(user.getUsername());
            dbManager.createUser(db_user);
            return new ResponseEntity<>("{\"message\": \"ok\"}", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("{\"message\": \"user already exists\"}", HttpStatus.BAD_REQUEST);

        }

    }

    @PostMapping("/user/auth")
    @ResponseBody
    public ResponseEntity<String> authUser(@RequestBody User user) throws JsonProcessingException {
        try {
            UsersEntity db_user = dbManager.getUser(user.getUsername());

            if (PasswordHasher.verifyPassword(user.getPassword(), db_user.getHashPassword())) {
                String JWTtoken = JwtUtil.generateToken(db_user.getUsername());
                Map<String, String> response = new HashMap<>();
                response.put("token", JWTtoken);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(response);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {

                return new ResponseEntity<>("{\"message\": \"failed\"}", HttpStatus.UNAUTHORIZED);
            }
        }
        catch (NullPointerException e){
            return new ResponseEntity<>("{\"message\": \"User not found\"}", HttpStatus.NOT_FOUND);
        }
    }





    @GetMapping("/screenshot")
    public ResponseEntity<Resource> getUserPhoto(@RequestHeader("Authorization") String authHeader) throws IOException {
        if (JwtUtil.validateToken(authHeader)) {


            screenshot.makeSreen();
            Path path = Paths.get("src/screenshot/scr.jpg");
            byte[] data = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(data.length)
                    .body(resource);
        }
        else{
            Path path = Paths.get("src/screenshot/cat_sad.jpg");
            byte[] data = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentLength(data.length)
                    .body(resource);
        }
    }


}