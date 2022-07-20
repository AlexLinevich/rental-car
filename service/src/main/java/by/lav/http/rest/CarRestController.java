package by.lav.http.rest;

import by.lav.dto.CarCreateEditDto;
import by.lav.dto.CarReadDto;
import by.lav.dto.UserCreateEditDto;
import by.lav.dto.UserReadDto;
import by.lav.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarRestController {

    private final CarService carService;

    @GetMapping
    public List<CarReadDto> findAll() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    public CarReadDto findById(@PathVariable("id") Integer id) {
        return carService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/carImage", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] findAvatar(@PathVariable("id") Integer id) {
        return carService.findCarImage(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CarReadDto create(@RequestBody CarCreateEditDto car) {
        return carService.create(car);
    }

    @PutMapping("/{id}")
    public CarReadDto update(@PathVariable("id") Integer id, @RequestBody CarCreateEditDto car) {
        return carService.update(id, car)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!carService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
