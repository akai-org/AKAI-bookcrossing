package pl.akai.bookcrossing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ebook {
    private int id;
    private String googleId;
    private String title;
    private String author;
    private String description;
    private String url;
    private List<Tag> tagList = new ArrayList<>();
}
