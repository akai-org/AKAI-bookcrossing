package pl.akai.bookcrossing.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.akai.bookcrossing.model.ResourceForm;
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

    public void insertNewTags(ResourceForm resourceForm) {
        String newTags = resourceForm.getNewTagsNames();
        if (newTags != null) {
            String[] tagNames = newTags.split(",");
            for (String name : tagNames) {
                var tag = new Tag();
                tag.setName(name.trim());
                var existingTag = tagDao.getTagByName(tag.getName());
                if (existingTag == null && tag.getName().length() != 0) {
                    tagDao.insertTag(tag);
                    tagDao.insertResourceTag(resourceForm.getId(), tag.getId());
                } else if (existingTag != null) {
                    resourceForm.addTagIdToExistingTagsList(existingTag.getId());
                }
            }
        }
    }

    public void insertExistingTags(ResourceForm resourceForm) {
        Set<Integer> existingTagsIdList = resourceForm.getExistingTagsIdList();
        if (existingTagsIdList != null) {
            for (Integer tagId : existingTagsIdList) {
                tagDao.insertResourceTag(resourceForm.getId(), tagId);
            }
        }
    }
}
