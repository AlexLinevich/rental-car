package by.lav.entity;

import by.lav.util.HibernateUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserIT {

    @Test
    void checkUser() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
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
            session.evict(user);
            User user1 = session.get(User.class, 1);
            System.out.println(user1);
            Assertions.assertThat(user1.getFirstName().equals("Alex"));

            user1.setFirstName("Ivan");
            session.update(user1);

            session.flush();
            session.evict(user1);
            User user2 = session.get(User.class, 1);
            System.out.println(user2);
            Assertions.assertThat(user2.getFirstName().equals("Ivan"));

            session.delete(user2);
            session.flush();
            session.evict(user2);

            User user3 = session.get(User.class, 1);
            System.out.println(user3);
            Assertions.assertThat(user3 == null);

            session.getTransaction().commit();
        }
    }
}
