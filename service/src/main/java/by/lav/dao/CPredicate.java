package by.lav.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CPredicate {

    List<Predicate> predicates = new ArrayList<>();

    private CPredicate() {
    }

    public static CPredicate builder(CriteriaBuilder cb) {
        return new CPredicate();
    }

    public <T> CPredicate add(T obj, Function<T, Predicate> function) {
        if (obj != null) {
            predicates.add(function.apply(obj));
        }
        return this;
    }

    public Predicate[] getPredicates() {
        return predicates.toArray(Predicate[]::new);
    }
}