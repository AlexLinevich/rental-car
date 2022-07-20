package by.lav.mapper;

import by.lav.dto.ClientDataReadDto;
import by.lav.dto.UserReadDto;
import by.lav.entity.ClientData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientDataReadMapper implements Mapper<ClientData, ClientDataReadDto> {

    private final UserReadMapper userReadMapper;

    @Override
    public ClientDataReadDto map(ClientData object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        return new ClientDataReadDto(
                object.getId(),
                user,
                object.getDriverLicenceNo(),
                object.getDlExpirationDay(),
                object.getCreditAmount()
        );
    }
}
