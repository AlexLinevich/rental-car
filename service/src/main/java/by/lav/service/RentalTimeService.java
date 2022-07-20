package by.lav.service;

import by.lav.dto.RentalTimeCreateEditDto;
import by.lav.dto.RentalTimeReadDto;
import by.lav.mapper.RentalTimeCreateEditMapper;
import by.lav.mapper.RentalTimeReadMapper;
import by.lav.repository.RentalTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalTimeService {

    private final RentalTimeRepository rentalTimeRepository;
    private final RentalTimeReadMapper rentalTimeReadMapper;
    private final RentalTimeCreateEditMapper rentalTimeCreateEditMapper;

    public List<RentalTimeReadDto> findAll() {
        return rentalTimeRepository.findAll().stream()
                .map(rentalTimeReadMapper::map)
                .collect(toList());
    }

    public Optional<RentalTimeReadDto> findById(Integer id) {
        return rentalTimeRepository.findById(id)
                .map(rentalTimeReadMapper::map);
    }

    @Transactional
    public RentalTimeReadDto create(RentalTimeCreateEditDto rentalTimeCreateEditDto) {
        return Optional.of(rentalTimeCreateEditDto)
                .map(rentalTimeCreateEditMapper::map)
                .map(rentalTimeRepository::save)
                .map(rentalTimeReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<RentalTimeReadDto> update(Integer id, RentalTimeCreateEditDto rentalTimeCreateEditDto) {
        return rentalTimeRepository.findById(id)
                .map(entity -> rentalTimeCreateEditMapper.map(rentalTimeCreateEditDto, entity))
                .map(rentalTimeRepository::saveAndFlush)
                .map(rentalTimeReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return rentalTimeRepository.findById(id)
                .map(entity -> {
                    rentalTimeRepository.delete(entity);
                    rentalTimeRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
