package org.example.peanuts.web;

import org.example.peanuts.bowl.Bowl;
import org.example.peanuts.bowl.Bowls;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class HalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Bowls bowls;

    @Test
    void testEmptyBowl() throws Exception {
        Bowl bowl = bowls.newBowl();

        mockMvc.perform(get("/bowls/{bowlId}",bowl.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self").exists())
            .andExpect(jsonPath("$._links.snack").doesNotExist())
            .andExpect(jsonPath("$._links.fill").exists())
            .andExpect(jsonPath("$._links.delete").exists());
    }

    @Test
    void testFilledBowl() throws Exception {
        Bowl bowl = bowls.newBowl();
        bowl.fill(10);

        mockMvc.perform(get("/bowls/{bowlId}",bowl.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self").exists())
            .andExpect(jsonPath("$._links.snack").exists())
            .andExpect(jsonPath("$._links.fill").exists())
            .andExpect(jsonPath("$._links.delete").doesNotExist());
    }

}

