package by.lav.entity;

import by.lav.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserIT {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateUtil.buildSessionFactory();
    }

    @AfterAll
    static void clean() {
        sessionFactory.close();
    }

    @Test
    void checkSaveUser() {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = User.builder()
                    .firstName("Alex")
                    .lastName("Ivanov")
                    .email("alex_ivanov@gmail.com")
                    .password("123")
                    .role(Role.ADMIN)
                    .build();
            session.persist(user);
            assertNotNull(user.getId());

            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkGetUser() {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = User.builder()
                    .firstName("Alex")
                    .lastName("Ivanov")
                    .email("alex_ivanov@gmail.com")
                    .password("123")
                    .role(Role.ADMIN)
                    .build();
            session.save(user);

            session.flush();
            var firstName = session.get(User.class, user.getId()).getFirstName();
            assertThat(firstName).isEqualTo("Alex");

            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkUpdateUser() {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var user = User.builder()
                    .firstName("Alex")
                    .lastName("Ivanov")
                    .email("alex_ivanov@gmail.com")
                    .password("123")
                    .role(Role.ADMIN)
                    .build();
            session.save(user);

            session.flush();
            User user1 = session.get(User.class, user.getId());
            user1.setFirstName("Ivan");
            session.update(user1);

            session.flush();
            User user2 = session.get(User.class, user.getId());
            assertThat(user2.getFirstName()).isEqualTo("Ivan");

            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkDeleteUser() {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = User.builder()
                    .firstName("Alex")
                    .lastName("Ivanov")
                    .email("alex_ivanov@gmail.com")
                    .password("123")
                    .role(Role.ADMIN)
                    .build();
            session.save(user);

            session.delete(user);
            session.flush();

            User user1 = session.get(User.class, user.getId());
            assertThat(user1 == null);

            session.getTransaction().commit();
        }
    }
}
