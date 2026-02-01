package com.example.comicbe.jpa.specs;

import com.example.comicbe.jpa.entity.*;
import com.example.comicbe.payload.filter.MangaFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
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
                predicates.add(criteriaBuilder.like(root.get(Manga_.AUTHOR), "%" + mangaFilter.getAuthor().strip() + "%"));
            }

//            if (StringUtils.hasText(mangaFilter.getCategory())) {
//                predicates.add(criteriaBuilder.like(root.get(Manga_.CHAPTERS), mangaFilter.getCategory().strip()));
//            }

            if (StringUtils.hasText(mangaFilter.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get(Manga_.TITLE), "%" + mangaFilter.getTitle().strip() + "%"));
            }

            if (StringUtils.hasText(mangaFilter.getCategory())) {
                predicates.add(criteriaBuilder.equal(root.join(Manga_.category).get(MangaCategory_.code), mangaFilter.getCategory()));
            }

//            if (StringUtils.hasText(mangaFilter.getCategory())) {
//
//                Join<Manga, MangaCategory> categoryJoin =
//                        root.join(Manga_.category, JoinType.INNER);
//
//                predicates.add(
//                        criteriaBuilder.like(
//                                criteriaBuilder.lower(categoryJoin.get(MangaCategory_.code)),
//                                "%" +  mangaFilter.getCategory().trim().toLowerCase()  + "%"
//                        )
//                );
//            }

            if (!CollectionUtils.isEmpty(mangaFilter.getGenre())) {
//                predicates.add(new CriteriaBuilder.In(root.join(MangaGenre_.MANGAS).get(String.valueOf(MangaGenre_.code)), mangaFilter.getGenre()));
            }


            return concatenatePredicate(predicates, criteriaBuilder);
        });
    }


    public Predicate concatenatePredicate(List<Predicate> predicates, CriteriaBuilder cb) {
        int size = predicates.size();
        return size > 0 ? cb.and((Predicate[]) predicates.toArray(new Predicate[size])) : cb.conjunction();
    }
}
