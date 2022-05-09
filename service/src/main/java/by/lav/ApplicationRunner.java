package by.lav;

import by.lav.config.ApplicationConfiguration;
import by.lav.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
            UserRepository userRepository = context.getBean(UserRepository.class);
            System.out.println(userRepository.findAll());
        }
    }
}
