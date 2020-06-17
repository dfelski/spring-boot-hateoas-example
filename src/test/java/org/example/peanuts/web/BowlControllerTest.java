package org.example.peanuts.web;

import org.example.peanuts.bowl.Bowl;
import org.example.peanuts.bowl.Bowls;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BowlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Bowls bowls;

    @Test
    void testCreateBowl() throws Exception {
        mockMvc.perform(post("/bowls"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.peanuts").value(0));
    }


    @Test
    void testGetBowl() throws Exception {
        Bowl bowl = bowls.newBowl();
        bowl.fill(10);
        mockMvc.perform(get("/bowls/{bowlId}",bowl.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.peanuts").value(10));
    }

    @Test
    void fillBowl() throws Exception {
        Bowl bowl = bowls.newBowl();
        mockMvc.perform(put("/bowls/{bowlId}/fillPeanuts",bowl.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"peanuts\" : 10}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.peanuts").value(10));
    }


    @Test
    void snackPeanuts() throws Exception {
        Bowl bowl = bowls.newBowl();
        bowl.fill(10);
        mockMvc.perform(post("/bowls/{bowlId}/snackPeanuts",bowl.getId().toString())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"peanuts\" : 3}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.peanuts").value(7));
    }

    @Test
    void deleteBowl() throws Exception {
        Bowl bowl = bowls.newBowl();

        mockMvc.perform(get("/bowls/{bowlId}", bowl.getId().toString()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/bowls/{bowlId}", bowl.getId().toString()))
                .andExpect(status().isAccepted());

        mockMvc.perform(get("/bowls/{bowlId}",bowl.getId().toString()))
                .andExpect(status().isNotFound());
    }
}