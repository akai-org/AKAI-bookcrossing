package pl.akai.bookcrossing.list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.akai.bookcrossing.login.CurrentUserService;
import pl.akai.bookcrossing.model.Book;
import pl.akai.bookcrossing.model.BookRentRequest;
import pl.akai.bookcrossing.model.Tag;
import pl.akai.bookcrossing.model.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookBean {

    private final BookDao bookDao;
    private final CurrentUserService currentUserService;

    public void insertBook(Book book) {
        User user = currentUserService.getCurrentUser();
        book.setOwner(user);
        bookDao.insertBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public Book getBookById(Integer id) {
        return bookDao.getBookById(id);
    }

    public List<Tag> getTagsByBookId(int id) {
        return bookDao.getTagsByBookId(id);
    }

    public List<Book> getBooksByTagId(int id) {
        return bookDao.getBooksByTagId(id);
    }

    void insertTag(Tag tag) {
        bookDao.insertTag(tag);
    }

    List<Tag> getAllTags() {
        return bookDao.getAllTags();
    }

    Tag getTagByName(String tagName) {
        return bookDao.getTagByName(tagName);
    }

    void insertBookTag(int bookId, int tagId) {
        bookDao.insertBookTag(bookId, tagId);
    }

    public List<Book> getBooksReadByCurrentUser() {
        User user = currentUserService.getCurrentUser();
        return bookDao.getBooksByReaderId(user.getId());
    }

    public List<Book> getBooksOwnedByCurrentUser() {
        User user = currentUserService.getCurrentUser();
        return bookDao.getBooksByOwnerId(user.getId());
    }

    public void updateReader(Integer requestId) {
        BookRentRequest request = bookDao.getBookRentRequestsById(requestId);
        bookDao.updateReader(request.getBook().getId(), request.getRequester().getId());
    }

    public void insertBookUserRequest(int bookId) {
        BookRentRequest bookRentRequest = BookRentRequest.builder()
                .requester(currentUserService.getCurrentUser())
                .book(bookDao.getBookById(bookId))
                .build();
        if (getBookRentRequestByOwnerAndBookIds(bookRentRequest) == 0)
            bookDao.insertBookUserRequest(bookRentRequest);
    }

    public List<BookRentRequest> getBookRentRequestsByOwnerId() {
        User user = currentUserService.getCurrentUser();
        return bookDao.getBookRentRequestsByOwnerId(user.getId());
    }

    public void deleteBookRentRequestsById(int id) {
        bookDao.deleteBookRentRequestsById(id);
    }

    Integer getBookRentRequestByOwnerAndBookIds(BookRentRequest bookRentRequest) {
        return bookDao.getBookRentRequestByOwnerAndBookIds(
                bookRentRequest
                        .getRequester()
                        .getId(),
                bookRentRequest
                        .getBook()
                        .getId());
    }

    public void updateIsAvailable(int bookId, boolean available) {
        bookDao.updateAvailable(bookId, available);
    }

    public void processBookRentRequestAcceptation(int requestId) {
        BookRentRequest request = bookDao.getBookRentRequestsById(requestId);
        updateIsAvailable(request.getBook().getId(), false);
        updateReader(requestId);
        deleteBookRentRequestsById(requestId);
    }
}