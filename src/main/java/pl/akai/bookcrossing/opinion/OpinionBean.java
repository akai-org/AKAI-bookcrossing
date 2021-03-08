package pl.akai.bookcrossing.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.akai.bookcrossing.login.CurrentUserService;
import pl.akai.bookcrossing.model.Opinion;
import pl.akai.bookcrossing.model.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpinionBean {

    private final OpinionDao opinionDao;
    private final CurrentUserService currentUserService;

    public void insertOpinion(Opinion opinion) {
        User user = currentUserService.getCurrentUser();
        opinion.setAuthor(user);
        opinionDao.insertOpinion(opinion);
    }

    public List<Opinion> getOpinionsByBookId(Integer id) {
        return opinionDao.getOpinionsByBookId(id);
    }

    public void updateOpinion(Opinion newOpinion) {
        var opinion = opinionDao.getOpinionById(newOpinion.getId());
        if (opinion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (isCurrentUserTheAuthor(opinion)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        opinionDao.updateOpinion(newOpinion);
    }

    private boolean isCurrentUserTheAuthor(Opinion opinion) {
        return opinion.getAuthor()
                      .getId() != currentUserService.getCurrentUser()
                                                    .getId();
    }
}
