package by.lav.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class ClientDataCreateEditDto {

    Integer userId;
    String driverLicenceNo;
    LocalDate dlExpirationDay;
    Double creditAmount;
}
