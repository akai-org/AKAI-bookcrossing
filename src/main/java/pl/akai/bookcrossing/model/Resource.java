package pl.akai.bookcrossing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
abstract class Resource {
    private int id;
    private String title;
    private String author;
    private String description;
    private List<Tag> tagList = new ArrayList<>();
}
