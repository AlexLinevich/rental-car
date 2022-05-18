package by.lav.repository;

import by.lav.entity.ClientData;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class ClientDataRepository extends RepositoryBase<Integer, ClientData> {

    public ClientDataRepository(EntityManager entityManager) {
        super(ClientData.class, entityManager);
    }
}