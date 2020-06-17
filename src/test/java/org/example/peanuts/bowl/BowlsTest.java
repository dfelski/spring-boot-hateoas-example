package org.example.peanuts.bowl;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BowlsTest {

    @Test
    void testCreateBowl(){
        Bowls bowls = new Bowls();
        Bowl bowl = bowls.newBowl();
        assertThat(bowl).isNotNull();

        UUID bowlId = bowl.getId();
        Bowl sameBowl = bowls.getBowl(bowlId);
        assertThat(bowl).isEqualTo(sameBowl);
    }

}