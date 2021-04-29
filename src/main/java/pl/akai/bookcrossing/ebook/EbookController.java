package pl.akai.bookcrossing.ebook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.akai.bookcrossing.login.CurrentUserService;
import pl.akai.bookcrossing.model.ResourceForm;
import pl.akai.bookcrossing.model.Opinion;
import pl.akai.bookcrossing.opinion.OpinionBean;
import pl.akai.bookcrossing.tag.TagBean;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ebooks")
public class EbookController {

    private final EbookBean ebookBean;
    private final OpinionBean opinionBean;
    private final TagBean tagBean;
    private final CurrentUserService currentUserService;

    @GetMapping("")
    public String ebooksList(Model model) {
        model.addAttribute("ebooks", ebookBean.getAllEbooks());
        return "views/ebooks-list";
    }

    @GetMapping("/{id}")
    public String ebookDetails(@PathVariable(name = "id") Integer id, Model model) {
        var book = ebookBean.getEbookById(id);
        var currentUser = currentUserService.getCurrentUser();
        model.addAttribute("tags", tagBean.getTagsByResourceId(id));
        List<Opinion> opinions = opinionBean.getOpinionsByBookId(id);
        model.addAttribute("resource", book);
        model.addAttribute("opinions", opinions);
        model.addAttribute("opinion", new Opinion());
        model.addAttribute("user", currentUser);
        return "views/resource-details";
    }

    @GetMapping("/{id}/edit")
    public String editBookForm(@PathVariable(name = "id") Integer id, Model model) {
        var ebook = ebookBean.getEbookById(id);
        model.addAttribute("resource", new ResourceForm(ebook));
        model.addAttribute("tags", tagBean.getAllTags());
        model.addAttribute("formTitle", "Formularz edycji ebooka");
        model.addAttribute("endpoint", String.format("/ebooks/%d/edit", id));
        return "views/resource-form";
    }

    @PostMapping("/{id}/edit")
    public String editBookSubmit(@PathVariable(name = "id") Integer id, @ModelAttribute ResourceForm resourceForm) {
//        var bookId = bookBean.insertBook(resourceForm.toBook());
//        resourceForm.setId(bookId);
//        tagBean.insertNewTags(resourceForm);
//        tagBean.insertExistingTags(resourceForm);
        return "redirect:/ebooks/" + resourceForm.getId();
    }
}
