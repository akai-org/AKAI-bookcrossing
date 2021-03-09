package pl.akai.bookcrossing.ebook;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pl.akai.bookcrossing.model.Ebook;

import java.util.List;

@Mapper
public interface EbookDaoMapper {

    List<Ebook> getAllEbooks();

    Ebook getEbookById(@Param("id") int id);

    void insertEbook(@Param("book") Ebook ebook);
}
 
