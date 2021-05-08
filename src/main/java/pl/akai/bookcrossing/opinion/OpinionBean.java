package pl.akai.bookcrossing.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.akai.bookcrossing.login.CurrentUserService;
import pl.akai.bookcrossing.model.Opinion;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpinionBean {

    private final OpinionDaoMapper opinionDao;
    private final CurrentUserService currentUserService;

    public void insertOpinion(Opinion opinion) {
        var user = currentUserService.getCurrentUser();
        opinion.setAuthor(user);
        opinionDao.insertOpinion(opinion);
    }

    public List<Opinion> getOpinionsByResourceId(Integer id) {
        return opinionDao.getOpinionsByResourceId(id);
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

    public void deleteOpinion(Integer id) {
        var toDelete = opinionDao.getOpinionById(id);
        if (toDelete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (isCurrentUserTheAuthor(toDelete)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        opinionDao.deleteOpinionById(id);
    }

    private boolean isCurrentUserTheAuthor(Opinion opinion) {
        return opinion.getAuthor()
                      .getId() != currentUserService.getCurrentUser()
                                                    .getId();
    }
}
