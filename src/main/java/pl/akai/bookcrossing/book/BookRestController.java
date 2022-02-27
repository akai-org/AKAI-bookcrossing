package pl.akai.bookcrossing.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.akai.bookcrossing.model.Book;
import pl.akai.bookcrossing.opinion.OpinionBean;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookBean bookBean;
    private final OpinionBean opinionBean;

    @GetMapping("/api/books")
    public List<Book> booksList() {
        return bookBean.getAllBooks();
    }

    @GetMapping("/api/books/{id}")
    public Dictionary<String, Object> bookDetails(@PathVariable(name = "id") Integer id) {
        Dictionary<String, Object> dict = new Hashtable<>();
        dict.put("book", bookBean.getBookById(id));
        dict.put("opinions", opinionBean.getOpinionsByResourceId(id));
        return dict;
    }

    @PostMapping("/books/rent")
    public ResponseEntity<Void> bookRental(@RequestBody Book book) {
        boolean success = bookBean.insertBookUserRequest(book.getId());
        if (success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping(value = "/my-books/change-available", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void bookChangeIsAvailable(@RequestBody Book book) {
        bookBean.updateIsAvailable(book.getId(), book.isAvailable());
    }

    @DeleteMapping("/my-books/accept/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void bookRentRequestAccept(@PathVariable(value = "id") Integer requestId) {
        bookBean.processBookRentRequestAcceptation(requestId);
    }

    @DeleteMapping("/my-books/decline/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void bookRentRequestDecline(@PathVariable(value = "id") Integer requestId) {
        bookBean.deleteBookRentRequestsById(requestId);
    }
}
