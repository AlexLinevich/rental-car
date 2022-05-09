package by.lav.config;

import by.lav.repository.UserRepository;
import by.lav.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "by.lav")
public class ApplicationConfiguration {

    @Bean
    public Session session() {
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        return (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(session());
    }
}
