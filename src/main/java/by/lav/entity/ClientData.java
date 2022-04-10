package by.lav.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client_data", schema = "public")
public class ClientData {

    @Id
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "driver_licence_no")
    private String driverLicenceNo;
    @Column(name = "dl_expiration_day")
    private LocalDate dlExpirationDay;
    @Column(name = "credit_amount")
    private Double creditAmount;
}
