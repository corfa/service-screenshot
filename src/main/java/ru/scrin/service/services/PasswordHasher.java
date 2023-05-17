package ru.scrin.service.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {

    public static final int BCRYPT_WORKLOAD = 12;


    public static String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_WORKLOAD));
        return hashedPassword;
    }

    public static boolean verifyPassword(String password, String hash) {
        boolean passwordVerified = BCrypt.checkpw(password, hash);
        return passwordVerified;
    }
}

