package pl.akai.bookcrossing.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Book extends Resource {
    private User owner;
    private User reader;
    private boolean isAvailable;

    @Builder
    public Book(int id,
                String title,
                String author,
                String description,
                List<Tag> tagList,
                User owner,
                User reader,
                boolean isAvailable) {
        super(id, title, author, description, tagList);
        this.owner = owner;
        this.reader = reader;
        this.isAvailable = isAvailable;
    }
}
