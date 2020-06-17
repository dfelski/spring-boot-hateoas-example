package org.example.peanuts.bowl;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class Bowls {

    private Map<UUID, Bowl> bowls = new HashMap<>();

    public Bowl newBowl() {
        Bowl bowl =  new Bowl();
        bowls.put(bowl.getId(), bowl);
        return bowl;
    }

    public Bowl getBowl(UUID id){
        return bowls.get(id);
    }

    public void removeBowl(UUID id){
        if(bowls.containsKey(id)){
            if(bowls.get(id).canDelete()) {
                bowls.remove(id);
            }
        }
    }
}
