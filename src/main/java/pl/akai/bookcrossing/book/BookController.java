package pl.akai.bookcrossing.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.akai.bookcrossing.model.Book;
import pl.akai.bookcrossing.model.BookFormResponse;
import pl.akai.bookcrossing.model.BookRentRequest;
import pl.akai.bookcrossing.model.Opinion;
import pl.akai.bookcrossing.opinion.OpinionBean;
import pl.akai.bookcrossing.tag.TagBean;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookBean bookBean;
    private final OpinionBean opinionBean;
    private final TagBean tagBean;

    @GetMapping("/")
    public String booksList(Model model) {
        model.addAttribute("books", bookBean.getAllBooks());
        return "index";
    }

    @GetMapping("/my-books")
    public String myBooksList(Model model) {
        List<BookRentRequest> requests = bookBean.getBookRentRequestsByReaderId();
        model.addAttribute("books_requests", requests);
        model.addAttribute("books_owner", bookBean.getBooksOwnedByCurrentUser());
        model.addAttribute("books_reader", bookBean.getBooksReadByCurrentUser());
        return "my-books";
    }

    @GetMapping("/book/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new BookFormResponse());
        model.addAttribute("tags", tagBean.getAllTags());
        return "form";
    }

    @PostMapping("/book/add")
    public String addBookSubmit(@ModelAttribute BookFormResponse bookFormResponse, Model model) {
        bookBean.insertBook(bookFormResponse);
        tagBean.insertNewTags(bookFormResponse);
        tagBean.insertExistingTags(bookFormResponse);
        model.addAttribute("book", bookFormResponse);
        return "redirect:/book/" + bookFormResponse.getId();
    }

    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(name = "id") Integer id, Model model) {
        Book book = bookBean.getBookById(id);
        model.addAttribute("tags", bookBean.getTagsByBookId(id));
        List<Opinion> opinions = opinionBean.getOpinionsByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("opinions", opinions);
        model.addAttribute("opinion", new Opinion());
        return "book-details";
    }
}
