package org.example.peanuts.web;

import org.example.peanuts.bowl.Bowl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BowlModelTest {

    @Test
    public void test(){
        Bowl bowl = new Bowl();
        bowl.fill(3);
        BowlModel bowlModel = new BowlModel(bowl);
        assertThat(bowlModel.getId()).isEqualTo(bowl.getId());
        assertThat(bowlModel.getPeanuts()).isEqualTo(3);
    }
}