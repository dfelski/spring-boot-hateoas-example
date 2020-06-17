package org.example.peanuts.web;

import org.example.peanuts.bowl.Bowl;
import org.example.peanuts.bowl.Bowls;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/bowls")
class BowlController {

    private Bowls bowls;

    BowlController(Bowls bowls){
        this.bowls = bowls;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    BowlModel createBowl(){
        Bowl bowl = bowls.newBowl();
        return map(bowl);
    }

    @GetMapping("/{bowlId}")
    @ResponseStatus(HttpStatus.OK)
    BowlModel getBowl(@PathVariable UUID bowlId){
        Bowl bowl = bowls.getBowl(bowlId);
        if(bowl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return map(bowl);
    }

    @PutMapping("/{bowlId}/fillPeanuts")
    protected BowlModel fillBowl(@PathVariable UUID bowlId, @RequestBody BowlModel bowlModel){
        Bowl bowl = bowls.getBowl(bowlId);
        bowl.fill(bowlModel.getPeanuts());
        return map(bowl);
    }

    @PostMapping("/{bowlId}/snackPeanuts")
    BowlModel snackPeanuts(@PathVariable UUID bowlId, @RequestBody BowlModel bowlModel){
        Bowl bowl = bowls.getBowl(bowlId);
        if(bowl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        bowl.snack(bowlModel.getPeanuts());
        return map(bowl);
    }

    @DeleteMapping("/{bowlId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void removeBowl(@PathVariable UUID bowlId){
        bowls.removeBowl(bowlId);
    }


    private BowlModel map(Bowl bowl){

        BowlModel bowlModel = new BowlModel(bowl);

        // self link
        bowlModel.add(WebMvcLinkBuilder.linkTo(BowlController.class)
                .slash(bowl.getId())
                .withSelfRel());

        if(bowl.canSnack()) {
            bowlModel.add(WebMvcLinkBuilder.linkTo(
                    methodOn(BowlController.class).snackPeanuts(bowl.getId(), null))
                    .withRel("snack"));
        }

        // fill is always possible...
        bowlModel.add(WebMvcLinkBuilder.linkTo(
                methodOn(BowlController.class).fillBowl(bowl.getId(), null))
                .withRel("fill"));

        if(bowl.canDelete()) {
            bowlModel.add(linkTo(BowlController.class).slash(bowl.getId())
                    .withRel("delete"));
        }

        return bowlModel;
    }
}
