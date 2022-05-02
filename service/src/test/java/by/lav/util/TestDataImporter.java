package by.lav.util;

import by.lav.entity.Car;
import by.lav.entity.CarCategory;
import by.lav.entity.ClientData;
import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import by.lav.entity.RentalTime;
import by.lav.entity.Role;
import by.lav.entity.User;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User robson = saveUser(session, "Bob", "Robson", "test1@tut.by", "test1", Role.ADMIN);
        User ivanov = saveUser(session, "Ivan", "Ivanov", "test2@tut.by", "test2", Role.CLIENT);
        User semenov = saveUser(session, "Alex", "Semenov", "test3@tut.by", "test3", Role.CLIENT);

        ClientData ivanovData = saveClientData(session, ivanov, "PB101121",
                LocalDate.of(2025, 8, 20), 1000.0);
        ClientData semenovData = saveClientData(session, semenov, "PB104466",
                LocalDate.of(2028, 1, 30), 2000.0);

        CarCategory middleSuv = saveCarCategory(session, "MIDDLE SUV", 50.0);
        CarCategory largeSuv = saveCarCategory(session, "LARGE SUV", 70.0);
        CarCategory largeSedan = saveCarCategory(session, "LARGE SEDAN", 60.0);

        Car toyotaCamry = saveCar(session, "TOYOTA CAMRY", "WHITE", 5, largeSedan);
        Car mazda6 = saveCar(session, "MAZDA 6", "BLACK", 5, largeSedan);
        Car toyotaLandcruiser = saveCar(session, "TOYOTA LANDCRUISER", "WHITE", 7, largeSuv);
        Car mazdaCX9 = saveCar(session, "MAZDA CX-9", "WHITE", 7, largeSuv);
        Car toyotaRAV4 = saveCar(session, "TOYOTA RAV4", "BLACK", 5, middleSuv);
        Car mazdaCX5 = saveCar(session, "MAZDA CX-5", "RED", 5, middleSuv);

        Order order1 = saveOrder(session,
                ivanov,
                toyotaCamry,
                LocalDateTime.of(2020, 1, 25, 12, 0),
                LocalDateTime.of(2020, 1, 29, 18, 0),
                OrderStatus.ACCEPTED);
        Order order2 = saveOrder(session,
                ivanov,
                mazdaCX9,
                LocalDateTime.of(2020, 2, 25, 12, 0),
                LocalDateTime.of(2020, 2, 28, 12, 0),
                OrderStatus.ACCEPTED);
        Order order3 = saveOrder(session,
                semenov,
                mazdaCX9,
                LocalDateTime.of(2020, 5, 25, 12, 0),
                LocalDateTime.of(2020, 5, 28, 12, 0),
                OrderStatus.ACCEPTED);
        Order order4 = saveOrder(session,
                semenov,
                mazda6,
                LocalDateTime.of(2020, 3, 25, 12, 0),
                LocalDateTime.of(2020, 3, 28, 12, 0),
                OrderStatus.CANCELED);

        saveRentalTime(session,
                order1,
                toyotaCamry,
                LocalDateTime.of(2020, 1, 25, 12, 0),
                LocalDateTime.of(2020, 1, 29, 18, 0));
        saveRentalTime(session,
                order2,
                mazdaCX9,
                LocalDateTime.of(2020, 2, 25, 12, 0),
                LocalDateTime.of(2020, 2, 28, 12, 0));
        saveRentalTime(session,
                order3,
                mazdaCX9,
                LocalDateTime.of(2020, 5, 25, 12, 0),
                LocalDateTime.of(2020, 5, 28, 12, 0));
        saveRentalTime(session,
                order4,
                mazda6,
                LocalDateTime.of(2020, 3, 25, 12, 0),
                LocalDateTime.of(2020, 3, 28, 12, 0));

        session.getTransaction().commit();
    }

    private User saveUser(Session session,
                          String firstName,
                          String lastName,
                          String email,
                          String password,
                          Role role) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .role(role)
                .build();
        session.save(user);

        return user;
    }

    private ClientData saveClientData(Session session,
                                      User user,
                                      String driverLicenceNo,
                                      LocalDate dlExpirationDay,
                                      Double creditAmount) {
        ClientData clientData = ClientData.builder()
                .user(user)
                .driverLicenceNo(driverLicenceNo)
                .dlExpirationDay(dlExpirationDay)
                .creditAmount(creditAmount)
                .build();
        session.save(clientData);

        return clientData;
    }

    private CarCategory saveCarCategory(Session session,
                                        String category,
                                        Double dayPrice) {
        CarCategory carCategory = CarCategory.builder()
                .category(category)
                .dayPrice(dayPrice)
                .build();
        session.save(carCategory);

        return carCategory;
    }

    private Car saveCar(Session session,
                        String model,
                        String colour,
                        Integer seatsQuantity,
                        CarCategory carCategory) {
        Car car = Car.builder()
                .model(model)
                .carCategory(carCategory)
                .colour(colour)
                .seatsQuantity(seatsQuantity)
                .build();
        session.save(car);

        return car;
    }

    private Order saveOrder(Session session,
                            User user,
                            Car car,
                            LocalDateTime beginTime,
                            LocalDateTime endTime,
                            OrderStatus status) {
        Order order = Order.builder()
                .user(user)
                .car(car)
                .beginTime(beginTime)
                .endTime(endTime)
                .status(status)
                .build();
        session.save(order);
        return order;
    }

    private void saveRentalTime(Session session,
                                Order order,
                                Car car,
                                LocalDateTime beginTime,
                                LocalDateTime endTime) {
        RentalTime rentalTime = RentalTime.builder()
                .car(car)
                .order(order)
                .beginTime(beginTime)
                .endTime(endTime)
                .build();
        session.save(rentalTime);
    }
}
