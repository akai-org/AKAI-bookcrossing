package pl.akai.bookcrossing.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.akai.bookcrossing.model.BookFormResponse;
import pl.akai.bookcrossing.model.Tag;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TagBean {

    private final TagDao tagDao;

    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    public void insertNewTags(BookFormResponse bookFormResponse) {
        String newTags = bookFormResponse.getNewTagsNames();
        if (newTags != null) {
            String[] tagNames = newTags.split(",");
            for (String name : tagNames) {
                Tag tag = new Tag();
                tag.setName(name.trim());
                Tag existingTag = tagDao.getTagByName(tag.getName());
                if (existingTag == null && tag.getName().length() != 0) {
                    tagDao.insertTag(tag);
                    tagDao.insertResourceTag(bookFormResponse.getId(), tag.getId());
                } else {
                    bookFormResponse.addTagId(tag.getId());
                }
            }
        }
    }

    public void insertExistingTags(BookFormResponse bookFormResponse) {
        Set<Integer> existingTagsIdList = bookFormResponse.getExistingTagsIdList();
        if (existingTagsIdList != null) {
            for (Integer tagId : existingTagsIdList) {
                tagDao.insertResourceTag(bookFormResponse.getId(), tagId);
            }
        }
    }
}
