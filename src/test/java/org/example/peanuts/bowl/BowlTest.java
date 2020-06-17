package org.example.peanuts.bowl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BowlTest {

    @Test
    void testBowl(){
        Bowl bowl = new Bowl();
        assertThat(bowl.level()).isEqualTo(0);
        bowl.fill(10);
        assertThat(bowl.level()).isEqualTo(10);
        bowl.snack(4);
        assertThat(bowl.level()).isEqualTo(6);
        Throwable thrown = catchThrowable(() -> { bowl.snack(10); });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}