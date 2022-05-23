package by.lav.repository;

import by.lav.entity.RentalTime;
import by.lav.repository.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@Sql({
        "classpath:sql/data.sql"
})
@RequiredArgsConstructor
public class RentalTimeRepositoryIT {

    private static final int ID_FIRST = 1;

    private final RentalTimeRepository rentalTimeRepository;

    @Test
    void checkSaveRentalTime() {
        RentalTime rentalTime = RentalTime.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        rentalTimeRepository.save(rentalTime);

        assertNotNull(rentalTime.getId());
    }

    @Test
    void checkDeleteRentalTime() {
        RentalTime rentalTime = RentalTime.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        rentalTimeRepository.save(rentalTime);

        rentalTimeRepository.delete(rentalTime);

        Optional<RentalTime> deletedRentalTime = rentalTimeRepository.findById(rentalTime.getId());
        assertTrue(deletedRentalTime.isEmpty());
    }

    @Test
    void checkUpdateRentalTime() {
        RentalTime rentalTime = RentalTime.builder()
                .beginTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .build();

        rentalTimeRepository.save(rentalTime);
        rentalTime.setBeginTime(LocalDateTime.of(2025, 1, 25, 12, 0));
        rentalTimeRepository.saveAndFlush(rentalTime);

        RentalTime updatedRentalTime = rentalTimeRepository.getById(rentalTime.getId());
        assertThat(updatedRentalTime.getBeginTime())
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
