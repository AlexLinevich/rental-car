package by.lav.repository;

import by.lav.dto.UserFilter;
import by.lav.entity.Role;
import by.lav.entity.User;
import by.lav.repository.annotation.IT;
import by.lav.util.TestDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@IT
@RequiredArgsConstructor
public class UserRepositoryIT {

    private static final int ID_FIRST = 1;

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initDb() {
        TestDataImporter.importData(entityManager);
    }

    @Test
    void checkSaveUser() {
        var user = User.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .email("alex_ivanov@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build();

        userRepository.save(user);

        assertNotNull(user.getId());
    }

    @Test
    void checkDeleteUser() {
        var user = User.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .email("alex_ivanov@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build();

        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser.getId());

        User user1 = entityManager.find(User.class, savedUser.getId());
        assertNull(user1);
    }

    @Test
    void checkUpdateUser() {
        var user = User.builder()
                .firstName("Alex")
                .lastName("Ivanov")
                .email("alex_ivanov@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build();

        User savedUser = userRepository.save(user);
        savedUser.setFirstName("Sveta");
        userRepository.update(savedUser);

        entityManager.flush();
        User user1 = entityManager.find(User.class, savedUser.getId());
        assertThat(user1.getFirstName()).isEqualTo("Sveta");
    }

    @Test
    void checkFindByIdUser() {
        Optional<User> user = userRepository.findById(ID_FIRST);

        assertThat(user).isNotNull();
        user.ifPresent(value -> assertThat(value.getFirstName()).isEqualTo("Bob"));
        user.ifPresent(value -> assertThat(value.getEmail()).isEqualTo("test1@tut.by"));
    }

    @Test
    void checkFindAllUsers() {
        List<User> results = userRepository.findAll();
        assertThat(results).hasSize(3);

        List<String> fullNames = results.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson", "Ivan Ivanov", "Alex Semenov");
    }

    @Test
    void findUserByEmailAndPasswordWithCriteriaAPI() {
        Optional<User> user = userRepository.findByEmailAndPasswordWithCriteriaAPI("test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Bob Robson"));
    }

    @Test
    void findUserByEmailAndPasswordWithQuerydsl() {
        Optional<User> user = userRepository.findByEmailAndPasswordWithQuerydsl("test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Bob Robson"));
    }

    @Test
    void findUsersByFirstNameAndLastNameWithCriteriaAPI() {
        UserFilter filter = UserFilter.builder()
                .firstName("Bob")
                .lastName("Robson")
                .build();

        List<User> users = userRepository.findByFirstNameAndLastNameWithCriteriaAPI(filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson");
    }

    @Test
    void findUsersByFirstNameAndLastNameWithQuerydsl() {
        UserFilter filter = UserFilter.builder()
                .firstName("Bob")
                .lastName("Robson")
                .build();
        List<User> users = userRepository.findByFirstNameAndLastNameWithQuerydsl(filter);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson");
    }
}
