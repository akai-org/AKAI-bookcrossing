package pl.akai.bookcrossing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Opinion {

    private int id;
    private int resourceId;
    private int rating;
    private String description;
    private User author;
}
