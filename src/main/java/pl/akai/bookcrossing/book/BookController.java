package pl.akai.bookcrossing.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.akai.bookcrossing.login.CurrentUserService;
import pl.akai.bookcrossing.model.BookRentRequest;
import pl.akai.bookcrossing.model.Opinion;
import pl.akai.bookcrossing.model.ResourceForm;
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
        return "views/books-list";
    }

    @GetMapping("/my-books")
    public String myBooksList(Model model) {
        List<BookRentRequest> requests = bookBean.getBookRentRequestsByReaderId();
        model.addAttribute("book_requests", requests);
        model.addAttribute("books_owner", bookBean.getBooksOwnedByCurrentUser());
        model.addAttribute("books_reader", bookBean.getBooksReadByCurrentUser());
        return "views/my-books";
    }

    @GetMapping("/books/add")
    public String addBookForm(Model model) {
        model.addAttribute("resource", new ResourceForm());
        model.addAttribute("tags", tagBean.getAllTags());
        model.addAttribute("formTitle", "Formularz dodania książki");
        model.addAttribute("endpoint", "/books/add");
        return "views/resource-form";
    }

    @PostMapping("/books/add")
    public String addBookSubmit(@ModelAttribute ResourceForm resourceForm) {
        var bookId = bookBean.insertBook(resourceForm.toBook());
        resourceForm.setId(bookId);
        tagBean.insertNewTags(resourceForm);
        tagBean.insertExistingTags(resourceForm);
        return "redirect:/books/" + resourceForm.getId();
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

    @GetMapping("/books/{id}/edit")
    public String editBookForm(@PathVariable(name = "id") Integer id, Model model) {
        var book = bookBean.getBookById(id);
        model.addAttribute("resource", new ResourceForm(book));
        model.addAttribute("tags", tagBean.getAllTags());
        model.addAttribute("formTitle", "Formularz edycji książki");
        model.addAttribute("endpoint", String.format("/books/%d/edit", id));
        return "views/resource-form";
    }

    @PostMapping("/books/{id}/edit")
    public String editBookSubmit(@PathVariable(name = "id") Integer id, @ModelAttribute ResourceForm resourceForm) {
//        var bookId = bookBean.insertBook(resourceForm.toBook());
//        resourceForm.setId(bookId);
//        tagBean.insertNewTags(resourceForm);
//        tagBean.insertExistingTags(resourceForm);
        return "redirect:/books/" + resourceForm.getId();
    }
}
