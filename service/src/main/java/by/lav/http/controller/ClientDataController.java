package by.lav.http.controller;

import by.lav.dto.ClientDataCreateEditDto;
import by.lav.dto.UserReadDto;
import by.lav.service.ClientDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client-data")
@RequiredArgsConstructor
public class ClientDataController {

    private final ClientDataService clientDataService;

    @GetMapping()
    public String clientDataPage(Model model,@ModelAttribute("createdUser") UserReadDto createdUser) {
        model.addAttribute("createdUser", createdUser);
        return "user/client-data";
    }

    @PostMapping()
    public String create(@ModelAttribute ClientDataCreateEditDto clientDataCreateEditDto) {
        clientDataService.create(clientDataCreateEditDto);

        return "redirect:/login";
    }
}
