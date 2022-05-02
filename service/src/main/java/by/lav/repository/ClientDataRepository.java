package by.lav.repository;

import by.lav.entity.ClientData;

import javax.persistence.EntityManager;

public class ClientDataRepository extends RepositoryBase<Integer, ClientData> {

    public ClientDataRepository(EntityManager entityManager) {
        super(ClientData.class, entityManager);
    }
}