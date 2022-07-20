package by.lav.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ClientDataReadDto {

    Integer id;
    UserReadDto user;
    String driverLicenceNo;
    LocalDate dlExpirationDay;
    Double creditAmount;
}
