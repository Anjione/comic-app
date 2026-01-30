package com.example.comicbe.jpa.specs;

import com.example.comicbe.jpa.entity.Manga;
import com.example.comicbe.jpa.entity.Manga_;
import com.example.comicbe.payload.filter.MangaFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MangaSpecs {
    public Specification<Manga> mangaFilterSpecification(MangaFilter mangaFilter) {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(mangaFilter.getAuthor())) {
                predicates.add(criteriaBuilder.like(root.get(Manga_.AUTHOR), mangaFilter.getAuthor().strip()));
            }

            if (StringUtils.hasText(mangaFilter.getCategory())) {
                predicates.add(criteriaBuilder.like(root.get(Manga_.CHAPTERS), mangaFilter.getCategory().strip()));
            }

            if (StringUtils.hasText(mangaFilter.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get(Manga_.TITLE), "%" + mangaFilter.getTitle().strip() + "%"));
            }


            return concatenatePredicate(predicates, criteriaBuilder);
        });
    }


    public Predicate concatenatePredicate(List<Predicate> predicates, CriteriaBuilder cb) {
        int size = predicates.size();
        return size > 0 ? cb.and((Predicate[]) predicates.toArray(new Predicate[size])) : cb.conjunction();
    }
}
