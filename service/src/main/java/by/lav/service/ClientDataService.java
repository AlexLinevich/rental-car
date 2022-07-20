package by.lav.service;

import by.lav.dto.ClientDataCreateEditDto;
import by.lav.dto.ClientDataReadDto;
import by.lav.mapper.ClientDataCreateEditMapper;
import by.lav.mapper.ClientDataReadMapper;
import by.lav.repository.ClientDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientDataService {

    private final ClientDataRepository clientDataRepository;
    private final ClientDataReadMapper clientDataReadMapper;
    private final ClientDataCreateEditMapper clientDataCreateEditMapper;

    public List<ClientDataReadDto> findAll() {
        return clientDataRepository.findAll().stream()
                .map(clientDataReadMapper::map)
                .collect(toList());
    }

    public Optional<ClientDataReadDto> findById(Integer id) {
        return clientDataRepository.findById(id)
                .map(clientDataReadMapper::map);
    }

    public Optional<ClientDataReadDto> findByUserId(Integer userId) {
        return clientDataRepository.findByUserId(userId)
                .map(clientDataReadMapper::map);
    }

    @Transactional
    public ClientDataReadDto create(ClientDataCreateEditDto clientDataDto) {
        return Optional.of(clientDataDto)
                .map(clientDataCreateEditMapper::map)
                .map(clientDataRepository::save)
                .map(clientDataReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ClientDataReadDto> update(Integer id, ClientDataCreateEditDto clientDataDto) {
        return clientDataRepository.findById(id)
                .map(entity -> clientDataCreateEditMapper.map(clientDataDto, entity))
                .map(clientDataRepository::saveAndFlush)
                .map(clientDataReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return clientDataRepository.findById(id)
                .map(entity -> {
                    clientDataRepository.delete(entity);
                    clientDataRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
