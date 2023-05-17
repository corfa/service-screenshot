package ru.scrin.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.Test;
import ru.scrin.service.services.JwtUtil;
import ru.scrin.service.services.Screenshot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilTest {

    @Test
    public void testMakeSreen() {

        Screenshot screenshot = new Screenshot();


        screenshot.makeSreen();

    }

    @Test
    public void testInvalidToken() {
        // Arrange
        String invalidToken = "invalidToken";
        String secretKey = "yourSecretKey";
        Jws<Claims> jws = mock(Jws.class);
        when(jws.getBody()).thenThrow(new RuntimeException());

        JwtUtil jwtUtil = new JwtUtil();

        // Act
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }
}
