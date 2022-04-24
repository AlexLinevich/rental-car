package by.lav.dao;

import by.lav.entity.RentalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RentalTimeDao {

    private static final RentalTimeDao INSTANCE = new RentalTimeDao();

    public List<RentalTime> findAll(Session session) {
        return session.createQuery("select rt from RentalTime rt", RentalTime.class)
                .list();
    }

    public static RentalTimeDao getInstance() {
        return INSTANCE;
    }
}
