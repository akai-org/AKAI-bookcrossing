package pl.akai.bookcrossing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookFormResponse extends Book {
    private Set<Integer> existingTagsIdList = new HashSet<>();
    private String newTagsNames;

    public void addTagIdToExistingTagsList(Integer tagId) {
        this.existingTagsIdList.add(tagId);
    }
}
