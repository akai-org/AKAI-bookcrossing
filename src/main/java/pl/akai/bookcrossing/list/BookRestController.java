package pl.akai.bookcrossing.list;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.akai.bookcrossing.model.Book;
import pl.akai.bookcrossing.model.Opinion;
import pl.akai.bookcrossing.opinion.OpinionBean;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookBean bookBean;
    private final OpinionBean opinionBean;

    @PostMapping("/book/rent")
    public ResponseEntity<Void> bookRental(@RequestBody Book book) {
        boolean duplicated = bookBean.insertBookUserRequest(book.getId());
        if (duplicated) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PostMapping("/book/{id}/add-opinion")
    @ResponseStatus(HttpStatus.CREATED)
    public void opinionSubmit(@PathVariable int id, @RequestBody Opinion opinion) {
        opinionBean.insertOpinion(opinion, id);
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
    public void bookRentRequestDecline(@PathVariable(value = "id")  Integer requestId) {
        bookBean.deleteBookRentRequestsById(requestId);
    }
}
