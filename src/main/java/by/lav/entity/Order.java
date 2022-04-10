package by.lav.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders", schema = "public")
public class Order {

    @Id
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "car_id")
    private Integer carId;
    @Column(name = "begin_time")
    private LocalDateTime beginTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private String status;
    private String message;
}
