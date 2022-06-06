package by.lav.service;

import by.lav.dto.CarCreateEditDto;
import by.lav.dto.CarReadDto;
import by.lav.mapper.CarCreateEditMapper;
import by.lav.mapper.CarReadMapper;
import by.lav.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    private final CarReadMapper carReadMapper;
    private final CarCreateEditMapper carCreateEditMapper;

    public List<CarReadDto> findAll() {
        return carRepository.findAll().stream()
                .map(carReadMapper::map)
                .collect(toList());
    }

    public Optional<CarReadDto> findById(Integer id) {
        return carRepository.findById(id)
                .map(carReadMapper::map);
    }

    @Transactional
    public CarReadDto create(CarCreateEditDto carDto) {
        return Optional.of(carDto)
                .map(carCreateEditMapper::map)
                .map(carRepository::save)
                .map(carReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CarReadDto> update(Integer id, CarCreateEditDto carDto) {
        return carRepository.findById(id)
                .map(entity -> carCreateEditMapper.map(carDto, entity))
                .map(carRepository::saveAndFlush)
                .map(carReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return carRepository.findById(id)
                .map(entity -> {
                    carRepository.delete(entity);
                    carRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
