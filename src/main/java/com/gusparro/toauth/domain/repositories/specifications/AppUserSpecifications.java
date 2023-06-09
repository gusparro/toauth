package com.gusparro.toauth.domain.repositories.specifications;

import com.gusparro.toauth.api.dtos.appuser.AppUserSearchFilter;
import com.gusparro.toauth.domain.entities.AppUser;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AppUserSpecifications {

    public static Specification<AppUser> searchFilter(AppUserSearchFilter searchFilter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(searchFilter.fullName())) {
                predicates.add(builder.like(builder.upper(root.get("fullName")), "%" + searchFilter.fullName().toUpperCase() + "%"));
            }

            if (StringUtils.hasLength(searchFilter.email())) {
                predicates.add(builder.like(builder.upper(root.get("email")), "%" + searchFilter.email().toUpperCase() + "%"));
            }

            if (StringUtils.hasLength(searchFilter.username())) {
                predicates.add(builder.like(builder.upper(root.get("username")), "%" + searchFilter.username().toUpperCase() + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
