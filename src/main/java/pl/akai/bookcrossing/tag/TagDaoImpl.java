package pl.akai.bookcrossing.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.akai.bookcrossing.model.Tag;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private final TagDaoMapper tagMapper;

    @Override
    public void insertTag(Tag tag) {
        tagMapper.insertTag(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.getAllTags();
    }

    @Override
    public Tag getTagByName(String tagName) {
        return tagMapper.getTagByName(tagName);
    }

    @Override
    public void insertResourceTag(int resourceId, int tagId) {
        tagMapper.insertResourceTag(resourceId, tagId);
    }


}
