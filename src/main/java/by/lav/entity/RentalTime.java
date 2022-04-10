package by.lav.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rental_time", schema = "public")
public class RentalTime {

    @Id
    private Integer id;
    @Column(name = "car_id")
    private Integer carId;
    @Column(name = "begin_time")
    private LocalDateTime beginTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "order_id")
    private Integer orderId;
}
