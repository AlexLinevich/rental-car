package by.lav.repository;

import by.lav.dao.QPredicate;
import by.lav.dto.UserFilter;
import by.lav.entity.Role;
import by.lav.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static by.lav.entity.QUser.user;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RequiredArgsConstructor
public class UserRepositoryIT extends IntegrationTestBase {

    private static final int ID_FIRST = 1;

    private final UserRepository userRepository;

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

        userRepository.save(user);

        userRepository.delete(user);

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertTrue(deletedUser.isEmpty());
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

        userRepository.save(user);
        user.setFirstName("Sveta");
        userRepository.saveAndFlush(user);

        User updatedUser = userRepository.getById(user.getId());
        assertThat(updatedUser.getFirstName()).isEqualTo("Sveta");
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
    void findUserByEmailAndPassword() {
        Optional<User> user = userRepository.findByEmailAndPassword("test1@tut.by", "test1");

        user.ifPresent(value -> assertThat(value).isNotNull());
        user.ifPresent(value -> assertThat(value.fullName()).isEqualTo("Bob Robson"));
    }

    @Test
    void findUsersByFirstNameAndLastName() {
        UserFilter filter = UserFilter.builder()
                .firstName("Bob")
                .lastName("Robson")
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::eq)
                .add(filter.getLastName(), user.lastName::eq)
                .buildAnd();

        List<User> users = (List<User>) userRepository.findAll(predicate);

        assertThat(users).hasSize(1);

        List<String> fullNames = users.stream().map(User::fullName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Bob Robson");
    }
}
