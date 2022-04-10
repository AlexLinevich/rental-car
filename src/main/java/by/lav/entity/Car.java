package by.lav.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    private Integer id;
    @Column(name = "model_id")
    private Integer modelId;
    @Column(name = "car_category_id")
    private Integer carCategoryId;
    private String colour;
    @Column(name = "seats_quantity")
    private Integer seatsQuantity;
}
