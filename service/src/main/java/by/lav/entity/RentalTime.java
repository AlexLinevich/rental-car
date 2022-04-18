package by.lav.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RentalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public void setCar(Car car) {
        this.car = car;
        this.car.getRentalTimes().add(this);
    }

    public void setOrder(Car car) {
        this.car = car;
        this.car.getRentalTimes().add(this);
    }
}
