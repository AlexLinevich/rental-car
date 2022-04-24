package by.lav.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
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
