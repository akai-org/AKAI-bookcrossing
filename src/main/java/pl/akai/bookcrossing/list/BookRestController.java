package pl.akai.bookcrossing.list;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.akai.bookcrossing.model.Book;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookBean bookBean;
    private final BookDao bookDao;

    @RequestMapping("/book/{id}/rent")
    @ResponseStatus(HttpStatus.OK)
    public void bookRental(@PathVariable(name = "id") Integer bookId) {
        bookBean.insertBookUserRequest(bookId);
    }

    @RequestMapping("/my-books/available-negation")
    @ResponseStatus(HttpStatus.OK)
    public void bookIsAvailableNegation(@RequestParam(value = "id") Integer bookId) {
        Book book = bookDao.getBookById(bookId);
        bookBean.updateIsAvailable(bookId, !book.isAvailable());
    }

    @RequestMapping("/my-books/accept")
    @ResponseStatus(HttpStatus.OK)
    public void bookRentRequestAccept(@RequestParam(value = "id") Integer requestId) {
        bookBean.processBookRentRequestAcceptation(requestId);
    }

    @RequestMapping("/my-books/decline")
    @ResponseStatus(HttpStatus.OK)
    public void bookRentRequestDecline(@RequestParam(value = "id") Integer requestId) {
        bookBean.deleteBookRentRequestsById(requestId);
    }
}
