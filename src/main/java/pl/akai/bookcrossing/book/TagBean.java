package pl.akai.bookcrossing.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.akai.bookcrossing.model.BookFormResponse;
import pl.akai.bookcrossing.model.Tag;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TagBean {

    private final BookBean bookBean;

    public void insertNewTags(BookFormResponse bookFormResponse) {
        String newTags = bookFormResponse.getNewTagsNames();
        if (newTags != null) {
            String[] tagNames = newTags.split(",");
            for (String name : tagNames) {
                Tag tag = new Tag();
                tag.setName(name.trim());
                Tag existingTag = bookBean.getTagByName(tag.getName());
                if (existingTag == null && tag.getName().length() != 0) {
                    bookBean.insertTag(tag);
                    bookBean.insertBookTag(bookFormResponse.getId(), tag.getId());
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
                bookBean.insertBookTag(bookFormResponse.getId(), tagId);
            }
        }
    }
}
