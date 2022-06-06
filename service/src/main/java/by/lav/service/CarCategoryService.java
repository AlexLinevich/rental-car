package by.lav.service;

import by.lav.dto.CarCategoryCreateEditDto;
import by.lav.dto.CarCategoryReadDto;
import by.lav.mapper.CarCategoryCreateEditMapper;
import by.lav.mapper.CarCategoryReadMapper;
import by.lav.repository.CarCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarCategoryService {

    private final CarCategoryRepository carCategoryRepository;
    private final CarCategoryReadMapper carCategoryReadMapper;
    private final CarCategoryCreateEditMapper carCategoryCreateEditMapper;

    public List<CarCategoryReadDto> findAll() {
        return carCategoryRepository.findAll().stream()
                .map(carCategoryReadMapper::map)
                .collect(toList());
    }

    public Optional<CarCategoryReadDto> findById(Integer id) {
        return carCategoryRepository.findById(id)
                .map(carCategoryReadMapper::map);
    }

    @Transactional
    public CarCategoryReadDto create(CarCategoryCreateEditDto carCategoryDto) {
        return Optional.of(carCategoryDto)
                .map(carCategoryCreateEditMapper::map)
                .map(carCategoryRepository::save)
                .map(carCategoryReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CarCategoryReadDto> update(Integer id, CarCategoryCreateEditDto carCategoryDto) {
        return carCategoryRepository.findById(id)
                .map(entity -> carCategoryCreateEditMapper.map(carCategoryDto, entity))
                .map(carCategoryRepository::saveAndFlush)
                .map(carCategoryReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return carCategoryRepository.findById(id)
                .map(entity -> {
                    carCategoryRepository.delete(entity);
                    carCategoryRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
