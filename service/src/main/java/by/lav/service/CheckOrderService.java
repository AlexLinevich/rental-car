package by.lav.service;

import by.lav.dto.ClientDataCreateEditDto;
import by.lav.dto.OrderCreateEditDto;
import by.lav.dto.RentalTimeCreateEditDto;
import by.lav.entity.ClientData;
import by.lav.entity.OrderStatus;
import by.lav.entity.RentalTime;
import by.lav.repository.CarRepository;
import by.lav.repository.ClientDataRepository;
import by.lav.repository.RentalTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckOrderService {

    private final RentalTimeRepository rentalTimeRepository;
    private final ClientDataRepository clientDataRepository;
    private final CarRepository carRepository;
    private final ClientDataService clientDataService;
    private final RentalTimeService rentalTimeService;

    public OrderCreateEditDto check(Integer id, OrderCreateEditDto orderDto) {
        var rentalTimeEntities = rentalTimeRepository.findAllByCarId(orderDto.getCarId());
        Integer userId = orderDto.getUserId();
        Optional<ClientData> clientDataEntity = clientDataRepository.findByUserId(userId);
        LocalDate dlExpirationDay = clientDataEntity.orElseThrow().getDlExpirationDay();
        double clientCreditAmount = clientDataEntity.orElseThrow().getCreditAmount();
        LocalDateTime beginTime = orderDto.getBeginTime();
        LocalDateTime endTime = orderDto.getEndTime();
        Double dayPrice = carRepository.findById(orderDto.getCarId()).orElseThrow().getCarCategory().getDayPrice();

        double endPrice = calculateEndPrice(dayPrice, beginTime, endTime);

        if (orderDto.getStatus().equals(OrderStatus.PROCESSING)) {
            String timeMassage = "";
            if (isCarReserved(beginTime, endTime, rentalTimeEntities)) {
                timeMassage = " Chosen time is not available. Choose other time or other car. ";
            }
            String moneyMassage = "";
            if (isMoneyNotEnough(endPrice, clientCreditAmount)) {
                moneyMassage = " Not enough money in your account. Add money and try again. ";
            }
            String dlExpirationMassage = "";
            if (isDlExpirationNotValid(endTime, dlExpirationDay)) {
                dlExpirationMassage = " Your driving license is not valid during car using time. " +
                        "Add valid driver license in your account. ";
            }
            String message = " Have a nice trip! ";
            OrderStatus orderStatus = OrderStatus.ACCEPTED;
            if (isCarReserved(beginTime, endTime, rentalTimeEntities)
                    || isMoneyNotEnough(endPrice, clientCreditAmount)
                    || isDlExpirationNotValid(endTime, dlExpirationDay)) {
                orderStatus = OrderStatus.CANCELED;
                message = String.format("%s %s %s", timeMassage, moneyMassage, dlExpirationMassage);
            } else {
                ClientDataCreateEditDto clientData = new ClientDataCreateEditDto(
                        userId,
                        clientDataEntity.orElseThrow().getDriverLicenceNo(),
                        dlExpirationDay,
                        (clientCreditAmount - endPrice));
                clientDataService.update(clientDataEntity.orElseThrow().getId(), clientData);

                RentalTimeCreateEditDto rentalTimeToUpdate = new RentalTimeCreateEditDto(
                        orderDto.getCarId(),
                        beginTime,
                        endTime,
                        id);
                rentalTimeService.create(rentalTimeToUpdate);

                message = message + "TOTAL PRICE:" + endPrice + "$";
            }

            orderDto.setStatus(orderStatus);
            orderDto.setMessage(message);
            return orderDto;
        }
        return orderDto;
    }

    private boolean isDlExpirationNotValid(LocalDateTime endTime, LocalDate dlExpirationDay) {
        return dlExpirationDay.isBefore(endTime.toLocalDate());
    }

    private boolean isMoneyNotEnough(double endPrice, double clientCreditAmount) {
        return endPrice > clientCreditAmount;
    }

    private boolean isCarReserved(LocalDateTime beginTime, LocalDateTime endTime, List<RentalTime> rentalTimeEntities) {
        boolean result = false;
        for (RentalTime rentalTimeEntity : rentalTimeEntities) {
            if ((beginTime.isAfter(rentalTimeEntity.getBeginTime()) && beginTime.isBefore(rentalTimeEntity.getEndTime())) ||
                    (endTime.isAfter(rentalTimeEntity.getBeginTime()) && endTime.isBefore(rentalTimeEntity.getEndTime()))) {
                result = true;
            }
        }
        return result;
    }

    private double calculateEndPrice(Double dayPrice, LocalDateTime beginTime, LocalDateTime endTime) {
        long days = ChronoUnit.DAYS.between(beginTime, endTime);
        return days * dayPrice;
    }
}
