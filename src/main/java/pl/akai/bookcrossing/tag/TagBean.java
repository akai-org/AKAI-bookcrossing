package pl.akai.bookcrossing.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.akai.bookcrossing.model.ResourceFormResponse;
import pl.akai.bookcrossing.model.Tag;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TagBean {

    private final TagDaoMapper tagDao;

    public List<Tag> getAllTags() {
        return tagDao.getAllTags();
    }

    public List<Tag> getTagsByResourceId(int id) {
        return tagDao.getTagsByResourceId(id);
    }

    public void insertNewTags(ResourceFormResponse resourceFormResponse) {
        String newTags = resourceFormResponse.getNewTagsNames();
        if (newTags != null) {
            String[] tagNames = newTags.split(",");
            for (String name : tagNames) {
                Tag tag = new Tag();
                tag.setName(name.trim());
                Tag existingTag = tagDao.getTagByName(tag.getName());
                if (existingTag == null && tag.getName().length() != 0) {
                    tagDao.insertTag(tag);
                    tagDao.insertResourceTag(resourceFormResponse.getId(), tag.getId());
                } else if (existingTag != null) {
                    resourceFormResponse.addTagIdToExistingTagsList(existingTag.getId());
                }
            }
        }
    }

    public void insertExistingTags(ResourceFormResponse resourceFormResponse) {
        Set<Integer> existingTagsIdList = resourceFormResponse.getExistingTagsIdList();
        if (existingTagsIdList != null) {
            for (Integer tagId : existingTagsIdList) {
                tagDao.insertResourceTag(resourceFormResponse.getId(), tagId);
            }
        }
    }
}
