package by.lav.util;

import by.lav.entity.Car;
import by.lav.entity.CarCategory;
import by.lav.entity.ClientData;
import by.lav.entity.Order;
import by.lav.entity.OrderStatus;
import by.lav.entity.RentalTime;
import by.lav.entity.Role;
import by.lav.entity.User;
import lombok.experimental.UtilityClass;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TestDataImporter {

    public void importData(EntityManager entityManager) {
        User robson = saveUser(entityManager, "Bob", "Robson", "test1@tut.by", "test1", Role.ADMIN);
        User ivanov = saveUser(entityManager, "Ivan", "Ivanov", "test2@tut.by", "test2", Role.CLIENT);
        User semenov = saveUser(entityManager, "Alex", "Semenov", "test3@tut.by", "test3", Role.CLIENT);

        ClientData ivanovData = saveClientData(entityManager, ivanov, "PB101121",
                LocalDate.of(2025, 8, 20), 1000.0);
        ClientData semenovData = saveClientData(entityManager, semenov, "PB104466",
                LocalDate.of(2028, 1, 30), 2000.0);

        CarCategory middleSuv = saveCarCategory(entityManager, "MIDDLE SUV", 50.0);
        CarCategory largeSuv = saveCarCategory(entityManager, "LARGE SUV", 70.0);
        CarCategory largeSedan = saveCarCategory(entityManager, "LARGE SEDAN", 60.0);

        Car toyotaCamry = saveCar(entityManager, "TOYOTA CAMRY", "WHITE", 5, largeSedan);
        Car mazda6 = saveCar(entityManager, "MAZDA 6", "BLACK", 5, largeSedan);
        Car toyotaLandcruiser = saveCar(entityManager, "TOYOTA LANDCRUISER", "WHITE", 7, largeSuv);
        Car mazdaCX9 = saveCar(entityManager, "MAZDA CX-9", "WHITE", 7, largeSuv);
        Car toyotaRAV4 = saveCar(entityManager, "TOYOTA RAV4", "BLACK", 5, middleSuv);
        Car mazdaCX5 = saveCar(entityManager, "MAZDA CX-5", "RED", 5, middleSuv);

        Order order1 = saveOrder(entityManager,
                ivanov,
                toyotaRAV4,
                LocalDateTime.of(2020, 1, 25, 12, 0),
                LocalDateTime.of(2020, 1, 29, 18, 0),
                OrderStatus.ACCEPTED);
        Order order2 = saveOrder(entityManager,
                robson,
                mazdaCX9,
                LocalDateTime.of(2020, 2, 25, 12, 0),
                LocalDateTime.of(2020, 2, 28, 12, 0),
                OrderStatus.ACCEPTED);
        Order order3 = saveOrder(entityManager,
                semenov,
                mazdaCX9,
                LocalDateTime.of(2020, 5, 25, 12, 0),
                LocalDateTime.of(2020, 5, 28, 12, 0),
                OrderStatus.ACCEPTED);
        Order order4 = saveOrder(entityManager,
                semenov,
                mazda6,
                LocalDateTime.of(2020, 3, 25, 12, 0),
                LocalDateTime.of(2020, 3, 28, 12, 0),
                OrderStatus.CANCELED);

        saveRentalTime(entityManager,
                order1,
                toyotaRAV4,
                LocalDateTime.of(2020, 1, 25, 12, 0),
                LocalDateTime.of(2020, 1, 29, 18, 0));
        saveRentalTime(entityManager,
                order2,
                mazdaCX9,
                LocalDateTime.of(2020, 2, 25, 12, 0),
                LocalDateTime.of(2020, 2, 28, 12, 0));
        saveRentalTime(entityManager,
                order3,
                mazdaCX9,
                LocalDateTime.of(2020, 5, 25, 12, 0),
                LocalDateTime.of(2020, 5, 28, 12, 0));
        saveRentalTime(entityManager,
                order4,
                mazda6,
                LocalDateTime.of(2020, 3, 25, 12, 0),
                LocalDateTime.of(2020, 3, 28, 12, 0));
    }

    private User saveUser(EntityManager entityManager,
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
        entityManager.persist(user);

        return user;
    }

    private ClientData saveClientData(EntityManager entityManager,
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
        entityManager.persist(clientData);

        return clientData;
    }

    private CarCategory saveCarCategory(EntityManager entityManager,
                                        String category,
                                        Double dayPrice) {
        CarCategory carCategory = CarCategory.builder()
                .category(category)
                .dayPrice(dayPrice)
                .build();
        entityManager.persist(carCategory);

        return carCategory;
    }

    private Car saveCar(EntityManager entityManager,
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
        entityManager.persist(car);

        return car;
    }

    private Order saveOrder(EntityManager entityManager,
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
        entityManager.persist(order);
        return order;
    }

    private void saveRentalTime(EntityManager entityManager,
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
        entityManager.persist(rentalTime);
    }
}
