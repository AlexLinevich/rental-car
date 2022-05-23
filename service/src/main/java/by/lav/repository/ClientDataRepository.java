package by.lav.repository;

import by.lav.entity.ClientData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDataRepository extends JpaRepository<ClientData,Integer> {
}