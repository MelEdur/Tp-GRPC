package com.unla.tp.util;

import org.springframework.data.jpa.domain.Specification;
import com.unla.tp.entity.TiendaEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TiendaSpecification implements Specification<TiendaEntity>{

    private SpecSearchCriteria criteria;

    public TiendaSpecification(final SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SpecSearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(final Root<TiendaEntity> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
        case EQUALITY:
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        case NEGATION:
            return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
        case GREATER_THAN:
            return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
        case LESS_THAN:
            return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
        case LIKE:
            return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
        case STARTS_WITH:
            return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
        case ENDS_WITH:
            return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
        case CONTAINS:
            return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        default:
            return null;
        }
    }
}
