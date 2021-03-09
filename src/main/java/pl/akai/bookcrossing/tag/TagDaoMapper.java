package pl.akai.bookcrossing.tag;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pl.akai.bookcrossing.model.Tag;

import java.util.List;


@Mapper
public interface TagDaoMapper {

    List<Tag> getAllTags();

    List<Tag> getTagsByResourceId(@Param("id") int id);

    Tag getTagByName(@Param("name") String tagName);

    void insertTag(@Param("tag") Tag tag);

    void insertResourceTag(@Param("resourceId") int resourceId, @Param("tagId") int tagId);

}
