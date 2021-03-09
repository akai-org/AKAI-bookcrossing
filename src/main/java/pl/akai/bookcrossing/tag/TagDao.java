package pl.akai.bookcrossing.tag;

import pl.akai.bookcrossing.model.Tag;

import java.util.List;

public interface TagDao {

    List<Tag> getAllTags();

    Tag getTagByName(String tagName);

    void insertTag(Tag tag);

    void insertResourceTag(int resourceId, int tagId);

}
