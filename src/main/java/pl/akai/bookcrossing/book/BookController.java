package pl.akai.bookcrossing.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.akai.bookcrossing.login.CurrentUserService;
import pl.akai.bookcrossing.model.ResourceFormResponse;
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
    private final CurrentUserService currentUserService;

    @GetMapping("/")
    public String booksList(Model model) {
        model.addAttribute("books", bookBean.getAllBooks());
        return "views/index";
    }

    @GetMapping("/my-books")
    public String myBooksList(Model model) {
        List<BookRentRequest> requests = bookBean.getBookRentRequestsByReaderId();
        model.addAttribute("books_requests", requests);
        model.addAttribute("books_owner", bookBean.getBooksOwnedByCurrentUser());
        model.addAttribute("books_reader", bookBean.getBooksReadByCurrentUser());
        return "views/my-books";
    }

    @GetMapping("/books/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new ResourceFormResponse());
        model.addAttribute("tags", tagBean.getAllTags());
        return "views/resource-form";
    }

    @PostMapping("/books/add")
    public String addBookSubmit(@ModelAttribute ResourceFormResponse resourceFormResponse) {
        var bookId = bookBean.insertBook(resourceFormResponse.toBook());
        resourceFormResponse.setId(bookId);
        tagBean.insertNewTags(resourceFormResponse);
        tagBean.insertExistingTags(resourceFormResponse);
        return "redirect:/books/" + resourceFormResponse.getId();
    }

    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable(name = "id") Integer id, Model model) {
        var book = bookBean.getBookById(id);
        var currentUser = currentUserService.getCurrentUser();
        model.addAttribute("tags", tagBean.getTagsByResourceId(id));
        List<Opinion> opinions = opinionBean.getOpinionsByBookId(id);
        model.addAttribute("resource", book);
        model.addAttribute("opinions", opinions);
        model.addAttribute("opinion", new Opinion());
        model.addAttribute("user", currentUser);
        return "views/resource-details";
    }
}
