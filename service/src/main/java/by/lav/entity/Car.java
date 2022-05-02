package by.lav.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_category_id")
    @ToString.Exclude
    private CarCategory carCategory;

    private String colour;

    private Integer seatsQuantity;

    private String image;

    @Builder.Default
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RentalTime> rentalTimes = new ArrayList<>();
}
