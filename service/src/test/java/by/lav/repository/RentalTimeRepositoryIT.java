package by.lav.repository;

import by.lav.entity.RentalTime;
import by.lav.repository.annotation.IT;
import by.lav.util.TestDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@IT
@RequiredArgsConstructor
public class RentalTimeRepositoryIT {

    private static final int ID_FIRST = 1;

    private final RentalTimeRepository rentalTimeRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initDb() {
        TestDataImporter.importData(entityManager);
    }

    @Test
    void checkSaveRentalTime() {
        RentalTime rentalTime = RentalTime.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        var save = rentalTimeRepository.save(rentalTime);

        assertNotNull(save.getId());
    }

    @Test
    void checkDeleteRentalTime() {
        RentalTime rentalTime = RentalTime.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        var savedRentalTime = rentalTimeRepository.save(rentalTime);

        rentalTimeRepository.delete(savedRentalTime.getId());

        RentalTime rentalTime1 = entityManager.find(RentalTime.class, savedRentalTime.getId());
        assertNull(rentalTime1);
    }

    @Test
    void checkUpdateRentalTime() {
        RentalTime rentalTime = RentalTime.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        var savedRentalTime = rentalTimeRepository.save(rentalTime);
        savedRentalTime.setBeginTime(LocalDateTime.of(2025, 1, 25, 12, 0));
        rentalTimeRepository.update(savedRentalTime);

        entityManager.flush();
        RentalTime rentalTime1 = entityManager.find(RentalTime.class, savedRentalTime.getId());
        assertThat(rentalTime1.getBeginTime())
                .isEqualTo(LocalDateTime.of(2025, 1, 25, 12, 0));
    }

    @Test
    void checkFindByIdRentalTime() {
        var beginTime = LocalDateTime.of(2020, 1, 25, 12, 0);
        var endTime = LocalDateTime.of(2020, 1, 29, 18, 0);

        Optional<RentalTime> rentalTime = rentalTimeRepository.findById(ID_FIRST);
        rentalTime.ifPresent(System.out::println);

        assertThat(rentalTime).isNotNull();
        rentalTime.ifPresent(value -> assertThat(value.getBeginTime()).isEqualTo(beginTime));
        rentalTime.ifPresent(value -> assertThat(value.getEndTime()).isEqualTo(endTime));
    }

    @Test
    void checkFindAllRentalTimes() {
        List<RentalTime> results = rentalTimeRepository.findAll();
        assertThat(results).hasSize(4);

        var beginTime1 = LocalDateTime.of(2020, 1, 25, 12, 0);
        var beginTime2 = LocalDateTime.of(2020, 2, 25, 12, 0);
        var beginTime3 = LocalDateTime.of(2020, 5, 25, 12, 0);
        var beginTime4 = LocalDateTime.of(2020, 3, 25, 12, 0);
        List<LocalDateTime> beginTimes = results.stream().map(RentalTime::getBeginTime).collect(toList());
        assertThat(beginTimes).containsExactlyInAnyOrder(beginTime1, beginTime2, beginTime3, beginTime4);
    }
}
