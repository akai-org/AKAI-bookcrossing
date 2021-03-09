package pl.akai.bookcrossing.book;

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
        book.setReader(user);
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

    public boolean insertBookUserRequest(int bookId) {
        BookRentRequest bookRentRequest = BookRentRequest.builder()
                .requester(currentUserService.getCurrentUser())
                .book(bookDao.getBookById(bookId))
                .build();
        if (isBookRentRequestDuplicated(bookRentRequest) == 0) {
            bookDao.insertBookUserRequest(bookRentRequest);
            return true;
        }
        return false;
    }

    public List<BookRentRequest> getBookRentRequestsByReaderId() {
        User user = currentUserService.getCurrentUser();
        return bookDao.getBookRentRequestsByReaderId(user.getId());
    }

    public void deleteBookRentRequestsById(int id) {
        bookDao.deleteBookRentRequestsById(id);
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

    private int isBookRentRequestDuplicated(BookRentRequest bookRentRequest) {
        return bookDao.getBookRentRequestByRequesterAndBookIds(
                bookRentRequest
                        .getRequester()
                        .getId(),
                bookRentRequest
                        .getBook()
                        .getId());
    }
}
