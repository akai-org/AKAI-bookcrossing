package pl.akai.bookcrossing.list;

import pl.akai.bookcrossing.model.Book;
import pl.akai.bookcrossing.model.BookRentRequest;
import pl.akai.bookcrossing.model.Tag;

import java.util.List;

public interface BookDao {

    Book getBookById(int bookId);

    List<Book> getAllBooks();

    void insertBook(Book book);

    List<Book> getBooksByOwnerId(int id);

    List<Book> getBooksByReaderId(int id);

    List<Tag> getTagsByBookId(int id);

    List<Book> getBooksByTagId(int id);

    void insertTag(Tag tag);

    List<Tag> getAllTags();

    Tag getTagByName(String tagName);

    void insertBookTag(int bookId, int tagId);

    void updateReader(int bookId, int readerId);

    void insertBookUserRequest(BookRentRequest bookRentRequest);

    List<BookRentRequest> getBookRentRequestsByReaderId(int readerId);

    BookRentRequest getBookRentRequestsById(int id);

    void deleteBookRentRequestsById(int id);

    Integer getBookRentRequestByRequesterAndBookIds(int requesterId, int bookId);

    void updateAvailable(int bookId, boolean available);

}
