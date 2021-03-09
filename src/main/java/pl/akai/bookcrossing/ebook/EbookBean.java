package pl.akai.bookcrossing.ebook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.akai.bookcrossing.model.Ebook;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EbookBean {

    private final EbookDaoMapper ebookDao;

    public List<Ebook> getAllEbooks() {
        return ebookDao.getAllEbooks();
    }

    public Ebook getEbookById(int id) {
        return ebookDao.getEbookById(id);
    }

    public void insertEbook(Ebook ebook) {
        ebookDao.insertEbook(ebook);
    }
}
