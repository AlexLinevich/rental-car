package by.lav.mapper;

import by.lav.dto.ClientDataCreateEditDto;
import by.lav.entity.ClientData;
import by.lav.entity.User;
import by.lav.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientDataCreateEditMapper implements Mapper<ClientDataCreateEditDto, ClientData> {

    private final UserRepository userRepository;

    @Override
    public ClientData map(ClientDataCreateEditDto fromObject, ClientData toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public ClientData map(ClientDataCreateEditDto object) {
        ClientData clientData = new ClientData();
        copy(object, clientData);
        return clientData;
    }

    private void copy(ClientDataCreateEditDto object, ClientData clientData) {
        clientData.setUser(getUser(object.getUserId()));
        clientData.setDriverLicenceNo(object.getDriverLicenceNo());
        clientData.setDlExpirationDay(object.getDlExpirationDay());
        clientData.setCreditAmount(object.getCreditAmount());
    }

    public User getUser(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
