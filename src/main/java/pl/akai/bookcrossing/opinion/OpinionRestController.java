package pl.akai.bookcrossing.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.akai.bookcrossing.model.Opinion;

@RestController
@RequestMapping("/opinion")
@RequiredArgsConstructor
public class OpinionRestController {

    private final OpinionBean opinionBean;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void opinionSubmit(@RequestBody Opinion opinion) {
        opinionBean.insertOpinion(opinion);
    }

}
