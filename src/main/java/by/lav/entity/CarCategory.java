package by.lav.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car_category", schema = "public")
public class CarCategory {

    @Id
    private Integer id;
    private String category;
    @Column(name = "day_price")
    private Double dayPrice;
}
