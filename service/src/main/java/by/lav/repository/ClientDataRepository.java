package by.lav.repository;

import by.lav.entity.ClientData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientDataRepository extends JpaRepository<ClientData, Integer> {

    Optional<ClientData> findByUserId(Integer userId);
}