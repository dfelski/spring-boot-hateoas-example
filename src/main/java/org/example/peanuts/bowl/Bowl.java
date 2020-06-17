package org.example.peanuts.bowl;

import java.util.Objects;
import java.util.UUID;

public class Bowl {

    private UUID id;
    private int peanuts;

    public Bowl(){
        id = UUID.randomUUID();
        this.peanuts = 0;
    }

    public UUID getId() {
        return id;
    }

    public void fill(int newPeanuts){
        this.peanuts += newPeanuts;
    }

    public void snack(int takenPeanuts){
        if(canSnack() && takenPeanuts > peanuts){
            throw new IllegalArgumentException("not enough peanuts");
        }
        this.peanuts -= takenPeanuts;
    }

    // snack link only on non empty bowls
    public boolean canSnack(){
        return peanuts > 0;
    }

    // deletion only allowed when not empty
    public boolean canDelete(){
        return peanuts == 0;
    }

    public int level(){
        return peanuts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bowl bowl = (Bowl) o;
        return peanuts == bowl.peanuts &&
                id.equals(bowl.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peanuts);
    }
}
