package by.lav.service;

import by.lav.dto.UserCreateEditDto;
import by.lav.dto.UserReadDto;
import by.lav.entity.Role;
import by.lav.repository.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private static final Integer USER_1 = 1;

    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(3);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("test1@tut.by", user.getEmail()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "Test",
                "Test",
                "test@gmail.com",
                "123",
                Role.ADMIN
        );
        UserReadDto actualResult = userService.create(userDto);

        assertEquals(userDto.getFirstName(), actualResult.getFirstName());
        assertEquals(userDto.getLastName(), actualResult.getLastName());
        assertEquals(userDto.getEmail(), actualResult.getEmail());
        assertEquals(userDto.getPassword(), actualResult.getPassword());
        assertSame(userDto.getRole(), actualResult.getRole());
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "Test",
                "Test",
                "test@gmail.com",
                "123",
                Role.ADMIN
        );

        Optional<UserReadDto> actualResult = userService.update(USER_1, userDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(userDto.getFirstName(), user.getFirstName());
            assertEquals(userDto.getLastName(), user.getLastName());
            assertEquals(userDto.getEmail(), user.getEmail());
            assertEquals(userDto.getPassword(), user.getPassword());
            assertSame(userDto.getRole(), user.getRole());
        });
    }

    @Test
    void delete() {
        assertFalse(userService.delete(-13));
        assertTrue(userService.delete(USER_1));
    }
}
