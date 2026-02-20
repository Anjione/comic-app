package com.example.comicbe.jpa.spectification.specs;

import com.example.comicbe.jpa.entity.Role;
import com.example.comicbe.jpa.entity.User;
import com.example.comicbe.jpa.spectification.base.SearchPageSpecification;
import com.example.comicbe.payload.filter.UserFilter;
import jakarta.persistence.criteria.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserSpec extends SearchPageSpecification<UserFilter, User> {

    public UserSpec(UserFilter searchPage) {
        super(searchPage);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, UserFilter search) {
        List<Predicate> predicates = new ArrayList<>();

        Join<User, Role> joinRole = root.join("roles", JoinType.LEFT);

        if (StringUtils.hasText(search.getUserLogged())) {
            Predicate equalUsername = criteriaBuilder.equal(root.get("username"), search.getUserLogged());
            Predicate equalEmail = criteriaBuilder.equal(root.get("email"), search.getUserLogged());
            predicates.add(criteriaBuilder.or(equalUsername, equalEmail));
        }

        if (StringUtils.hasText(search.getEmail())) {
            predicates.add(criteriaBuilder.like(root.get("email"), "%" + search.getEmail() + "%"));
        }

        if (StringUtils.hasText(search.getUsername())) {
            predicates.add(criteriaBuilder.like(root.get("username"), "%" + search.getUsername() + "%"));
        }

        if (StringUtils.hasText(search.getFullName())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + search.getFullName().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(search.getRole())) {
            predicates.add(criteriaBuilder.equal(joinRole.get("name"), search.getRole()));
        }

        if (search.getUserId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), search.getUserId()));
        }

        if (search.getEnabled() != null) {
            predicates.add(criteriaBuilder.equal(root.get("enabled"), search.getEnabled()));
        }

        if (search.getCreatedDateFrom() != null && search.getCreatedDateTo() != null) {
            predicates.add(criteriaBuilder.between(root.get("createdDate"), search.getCreatedDateFrom(), search.getCreatedDateTo()));
        }

        query.distinct(true);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
