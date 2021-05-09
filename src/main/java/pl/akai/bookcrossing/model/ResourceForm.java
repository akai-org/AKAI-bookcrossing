package pl.akai.bookcrossing.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ResourceForm extends Resource {
    private Set<Integer> existingTagsIdList = new HashSet<>();
    private String newTagsNames;

    public ResourceForm(Resource resource) {
        super(resource.getId(), resource.getTitle(), resource.getAuthor(), resource.getDescription(), Collections.emptyList());
        this.existingTagsIdList = new HashSet<>();
        for (Tag tag : resource.getTagList()) {
            this.existingTagsIdList.add(tag.getId());
        }
    }

    public void addTagIdToExistingTagsList(Integer tagId) {
        this.existingTagsIdList.add(tagId);
    }

    public Book toBook() {
        return Book.builder()
                .id(this.getId())
                .title(this.getTitle())
                .author(this.getAuthor())
                .description(this.getDescription())
                .tagList(this.getTagList())
                .build();
    }

    public Ebook toEbook() {
        return Ebook.builder()
                .id(this.getId())
                .title(this.getTitle())
                .author(this.getAuthor())
                .description(this.getDescription())
                .tagList(this.getTagList())
                .build();
    }
}
