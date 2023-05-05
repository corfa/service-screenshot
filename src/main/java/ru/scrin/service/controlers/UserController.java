package ru.scrin.service.controlers;


import Models.User;
import db.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.scrin.service.services.DBManager;
import ru.scrin.service.services.PasswordHasher;
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
    @Autowired
    private DBManager dbManager;



    @PostMapping("/user")
    public String addUser(@RequestBody User user) {
        UsersEntity db_user = new UsersEntity();
        String hash_password = PasswordHasher.hashPassword(user.getPassword());
        db_user.setHashPassword(hash_password);
        db_user.setUsername(user.getUsername());
        dbManager.createUser(db_user);
        return "success";
    }

    @PostMapping("/user/auth")
    public String authUser(@RequestBody User user) {
        UsersEntity db_user = dbManager.getUser(user.getUsername());
        if (PasswordHasher.verifyPassword(user.getPassword(),db_user.getHashPassword())){
            System.out.println("ok!");
            return "success";
        }
        else{
            System.out.println("not ok");

            return "no";
        }
    }





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