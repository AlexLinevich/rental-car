package by.lav.http.controller;

import by.lav.dto.CarCreateEditDto;
import by.lav.dto.CarFilter;
import by.lav.dto.CarReadDto;
import by.lav.dto.PageResponse;
import by.lav.service.CarCategoryService;
import by.lav.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarCategoryService carCategoryService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "car/cars";
    }

    @GetMapping("/main")
    public String startFindAll(Model model, CarFilter carFilter, Pageable pageable) {
        Page<CarReadDto> page = carService.findAll(carFilter, pageable);
        model.addAttribute("cars", PageResponse.of(page));
        model.addAttribute("filter", carFilter);
        return "car/main";
    }

    @GetMapping("/car-add")
    public String carAdd(Model model, @ModelAttribute("car") CarCreateEditDto car) {
        model.addAttribute("car", car);
        model.addAttribute("carCategories", carCategoryService.findAll());
        ;
        return "car/car-add";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return carService.findById(id)
                .map(car -> {
                    model.addAttribute("car", car);
                    model.addAttribute("carCategories", carCategoryService.findAll());
                    return "car/car";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/car-add")
    public String create(@ModelAttribute CarCreateEditDto car) {
        return "redirect:/cars/" + carService.create(car).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute CarCreateEditDto car) {
        return carService.update(id, car)
                .map(it -> "redirect:/cars/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!carService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/cars";
    }
}
