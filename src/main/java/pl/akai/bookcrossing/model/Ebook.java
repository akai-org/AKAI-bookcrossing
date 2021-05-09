package pl.akai.bookcrossing.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Ebook extends Resource {
    private String googleId;
    private String url;

    @Builder
    public Ebook(int id,
                 String title,
                 String author,
                 String description,
                 List<Tag> tagList,
                 String googleId,
                 String url) {
        super(id, title, author, description, tagList);
        this.googleId = googleId;
        this.url = url;
    }
}
