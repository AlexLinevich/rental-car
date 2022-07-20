package by.lav.service;

import by.lav.dao.QPredicate;
import by.lav.dto.CarCreateEditDto;
import by.lav.dto.CarFilter;
import by.lav.dto.CarReadDto;
import by.lav.entity.Car;
import by.lav.mapper.CarCreateEditMapper;
import by.lav.mapper.CarReadMapper;
import by.lav.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static by.lav.entity.QCar.car;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    private final CarReadMapper carReadMapper;
    private final CarCreateEditMapper carCreateEditMapper;
    private final ImageService imageService;

    public List<CarReadDto> findAll() {
        return carRepository.findAll().stream()
                .map(carReadMapper::map)
                .collect(toList());
    }

    public Page<CarReadDto> findAll(CarFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getColour(), car.colour::containsIgnoreCase)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        return carRepository.findAll(predicate, pageable)
                .map(carReadMapper::map);
    }

    public Optional<CarReadDto> findById(Integer id) {
        return carRepository.findById(id)
                .map(carReadMapper::map);
    }

    public Optional<byte[]> findCarImage(Integer id) {
        return carRepository.findById(id)
                .map(Car::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public CarReadDto create(CarCreateEditDto carDto) {
        return Optional.of(carDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return carCreateEditMapper.map(dto);
                })
                .map(carRepository::save)
                .map(carReadMapper::map)
                .orElseThrow();
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Transactional
    public Optional<CarReadDto> update(Integer id, CarCreateEditDto carDto) {
        return carRepository.findById(id)
                .map(entity -> {
                    uploadImage(carDto.getImage());
                    return carCreateEditMapper.map(carDto, entity);
                })
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
