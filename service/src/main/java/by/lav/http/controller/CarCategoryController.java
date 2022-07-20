package by.lav.http.controller;

import by.lav.dto.CarCategoryCreateEditDto;
import by.lav.service.CarCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/car-categories")
@RequiredArgsConstructor
public class CarCategoryController {

    private final CarCategoryService carCategoryService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("carCategories", carCategoryService.findAll());
        return "car/car-categories";
    }

    @GetMapping("/car-category-add")
    public String addCarCategory(Model model, @ModelAttribute CarCategoryCreateEditDto carCategory) {
        model.addAttribute("carCategory", carCategory);
        return "car/car-category-add";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return carCategoryService.findById(id)
                .map(carCategory -> {
                    model.addAttribute("carCategory", carCategory);
                    return "car/car-category";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/car-category-add")
    public String create(@ModelAttribute CarCategoryCreateEditDto carCategory) {
        return "redirect:/car-categories/" + carCategoryService.create(carCategory).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute CarCategoryCreateEditDto carCategory) {
        return carCategoryService.update(id, carCategory)
                .map(it -> "redirect:/car-categories/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!carCategoryService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/car-categories";
    }
}
