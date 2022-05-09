package by.lav.repository;

import by.lav.entity.ClientData;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class ClientDataRepository extends RepositoryBase<Integer, ClientData> {

    public ClientDataRepository(EntityManager entityManager) {
        super(ClientData.class, entityManager);
    }
}