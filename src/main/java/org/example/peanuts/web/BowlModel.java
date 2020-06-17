package org.example.peanuts.web;

import org.example.peanuts.bowl.Bowl;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

class BowlModel extends RepresentationModel<BowlModel> {

    private UUID id;
    private int peanuts;

    BowlModel(){
        id = null;
        peanuts = 0;
    }

    BowlModel(Bowl bowl){
        this.id = bowl.getId();
        this.peanuts = bowl.level();
    }

    public UUID getId() {
        return id;
    }

    public int getPeanuts() {
        return peanuts;
    }

    void setId(UUID id) {
        this.id = id;
    }

    void setPeanuts(int peanuts) {
        this.peanuts = peanuts;
    }
}
