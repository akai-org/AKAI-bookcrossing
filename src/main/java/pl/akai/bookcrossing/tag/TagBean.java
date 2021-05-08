package pl.akai.bookcrossing.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.akai.bookcrossing.model.ResourceForm;
import pl.akai.bookcrossing.model.Tag;

import java.util.HashSet;
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
        String[] tagNames = newTags.split(",");
        for (String name : tagNames) {
            var tag = new Tag(name.trim());
            var existingTag = tagDao.getTagByName(tag.getName());
            if (existingTag == null && tag.getName().length() != 0) {
                tagDao.insertTag(tag);
                tagDao.insertResourceTag(resourceForm.getId(), tag.getId());
            } else if (existingTag != null) {
                resourceForm.addTagIdToExistingTagsList(existingTag.getId());
            }
        }

    }

    public void insertExistingTags(ResourceForm resourceForm) {
        Set<Integer> existingTagsIdList = resourceForm.getExistingTagsIdList();
        for (Integer tagId : existingTagsIdList) {
            tagDao.insertResourceTag(resourceForm.getId(), tagId);
        }
    }

    public void updateTags(ResourceForm resourceForm) {
        insertNewTagsDuringEdit(resourceForm);
        Set<Integer> currentResourceTagIds = getResourceTagIds(resourceForm);
        addResourceTagDuringEdit(resourceForm, currentResourceTagIds);
        deleteResourceTagDuringEdit(resourceForm, currentResourceTagIds);
    }

    private void insertNewTagsDuringEdit(ResourceForm resourceForm) {
        String newTags = resourceForm.getNewTagsNames();
        String[] tagNames = newTags.split(",");
        for (String name : tagNames) {
            var tag = new Tag(name.trim());
            var existingTag = tagDao.getTagByName(tag.getName());
            if (existingTag == null && tag.getName().length() != 0) {
                tagDao.insertTag(tag);
                tagDao.insertResourceTag(resourceForm.getId(), tag.getId());
            }
            if (existingTag != null) {
                tag.setId(existingTag.getId());
            }
            if (tag.getName().length() != 0) {
                resourceForm.addTagIdToExistingTagsList(tag.getId());
            }
        }
    }

    private Set<Integer> getResourceTagIds(ResourceForm resourceForm) {
        List<Tag> resourceTagsInDatabase = tagDao.getTagsByResourceId(resourceForm.getId());
        Set<Integer> resourceTagIdsInDatabase = new HashSet<>();
        for (Tag tag : resourceTagsInDatabase) {
            resourceTagIdsInDatabase.add(tag.getId());
        }
        return resourceTagIdsInDatabase;
    }

    private void addResourceTagDuringEdit(ResourceForm resourceForm, Set<Integer> resourceTagIdsInDatabase) {
        Set<Integer> tagsToAdd = new HashSet<>(resourceForm.getExistingTagsIdList());
        tagsToAdd.removeAll(resourceTagIdsInDatabase);
        for (Integer tagId : tagsToAdd) {
            tagDao.insertResourceTag(resourceForm.getId(), tagId);
        }
    }

    private void deleteResourceTagDuringEdit(ResourceForm resourceForm, Set<Integer> resourceTagIdsInDatabase) {
        Set<Integer> tagsToDelete = new HashSet<>(resourceTagIdsInDatabase);
        tagsToDelete.removeAll(resourceForm.getExistingTagsIdList());
        for (Integer tagId : tagsToDelete) {
            tagDao.deleteResourceTag(resourceForm.getId(), tagId);
        }
    }
}
