package by.lav.http.controller;

import by.lav.dto.OrderCreateEditDto;
import by.lav.entity.OrderStatus;
import by.lav.entity.Role;
import by.lav.service.CarService;
import by.lav.service.OrderService;
import by.lav.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CarService carService;
    private final UserService userService;

    @GetMapping
    public String findAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/orders";
    }

    @GetMapping("/client-orders")
    public String findAllClientOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("orders", orderService.findAll());
        userService.findByEmail(userDetails.getUsername())
                .map(user -> {
                    model.addAttribute("currentUser", user);
                    model.addAttribute("roles", Role.values());
                    return "order/client-orders";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return "order/client-orders";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/check")
    public String checkOrder(@PathVariable("id") Integer id, @ModelAttribute OrderCreateEditDto order) {
        return orderService.checkAndUpdate(id, order)
                .map(it -> "redirect:/orders/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/make")
    public String orderAdd(Model model,
                           @ModelAttribute("order") OrderCreateEditDto order,
                           @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("order", order);
        model.addAttribute("orderStatus", OrderStatus.PROCESSING);
        model.addAttribute("cars", carService.findAll());
        userService.findByEmail(userDetails.getUsername())
                .map(user -> {
                    model.addAttribute("currentUser", user);
                    model.addAttribute("roles", Role.values());
                    return "order/make";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return "order/make";
    }

    @PostMapping("/make")
    public String create(@ModelAttribute OrderCreateEditDto order) {
        orderService.create(order);
        return "redirect:/orders/client-orders";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute OrderCreateEditDto order) {
        return orderService.update(id, order)
                .map(it -> "redirect:/orders/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!orderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/orders";
    }
}
