package by.lav.repository;

import by.lav.dao.CPredicate;
import by.lav.dao.QPredicate;
import by.lav.dto.UserFilter;
import by.lav.entity.User;
import by.lav.entity.User_;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

import static by.lav.entity.QUser.user;

public class UserRepository extends RepositoryBase<Integer, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public Optional<User> findByEmailAndPasswordWithCriteriaAPI(Session session, String email, String password) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user).where(
                cb.equal(user.get(User_.EMAIL), email),
                cb.equal(user.get(User_.PASSWORD), password)
        );

        return session.createQuery(criteria)
                .uniqueResultOptional();
    }

    public Optional<User> findByEmailAndPasswordWithQuerydsl(Session session, String email, String password) {
        return Optional.ofNullable(new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.email.eq(email), user.password.eq(password))
                .fetchOne());
    }

    public List<User> findByFirstNameAndLastNameWithCriteriaAPI(Session session, UserFilter filter) {
        var cb = session.getCriteriaBuilder();

        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        Predicate[] predicate = CPredicate.builder(cb)
                .add(filter.getFirstName(), firstName -> cb.equal(user.get(User_.firstName), firstName))
                .add(filter.getLastName(), lastName -> cb.equal(user.get(User_.lastName), lastName))
                .getPredicates();

        criteria.select(user).where(predicate);

        return session.createQuery(criteria)
                .list();
    }

    public List<User> findByFirstNameAndLastNameWithQuerydsl(Session session, UserFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.firstName::eq)
                .add(filter.getLastName(), user.lastName::eq)
                .buildAnd();

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
