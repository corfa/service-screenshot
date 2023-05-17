package ru.scrin.service;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.UsersEntity;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.scrin.service.services.DBManager;
import ru.scrin.service.services.PasswordHasher;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllersIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DBManager dbManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        user.setUsername("testUser5");
        user.setPassword("testPassword");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
        UsersEntity dbUser = dbManager.getUser("testUser");
        assertNotNull(dbUser);
        assertEquals("testUser", dbUser.getUsername());
    }
    @Test
    public void testAuthUser() throws Exception {

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        MvcResult result = mockMvc.perform(post("/api/user/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        JsonNode responseNode = objectMapper.readTree(responseJson);
        assertTrue(responseNode.has("token"));
        String token = responseNode.get("token").asText();
        assertNotNull(token);
    }
}


