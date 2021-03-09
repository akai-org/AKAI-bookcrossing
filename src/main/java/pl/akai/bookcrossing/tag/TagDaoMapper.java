package pl.akai.bookcrossing.tag;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pl.akai.bookcrossing.model.Tag;

import java.util.List;


@Mapper
public interface TagDaoMapper {

    void insertTag(@Param("tag") Tag tag);

    List<Tag> getAllTags();

    Tag getTagByName(@Param("name") String tagName);

    void insertResourceTag(@Param("resourceId") int resourceId, @Param("tagId") int tagId);

}
