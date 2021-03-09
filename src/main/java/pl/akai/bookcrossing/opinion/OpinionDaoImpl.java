package pl.akai.bookcrossing.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.akai.bookcrossing.model.Opinion;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OpinionDaoImpl implements OpinionDao {

    private final OpinionDaoMapper opinionMapper;

    @Override
    public List<Opinion> getOpinionsByResourceId(int bookId) {
        return opinionMapper.getOpinionsByResourceId(bookId);
    }

    @Override
    public void insertOpinion(Opinion opinion, int resourceId) {
        opinionMapper.insertOpinion(opinion, resourceId);
    }

    @Override
    public void updateOpinion(Opinion opinion) {
        opinionMapper.updateOpinion(opinion);
    }
}
