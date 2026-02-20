package com.example.comicbe.jpa.spectification.specs;

import com.example.comicbe.jpa.entity.*;
import com.example.comicbe.payload.filter.MangaFilter;
import jakarta.persistence.criteria.*;
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
//            Join<Manga, MangaGenre> genreJoin = root.join(Manga_.genres);
//
//            predicates.add(
//                    criteriaBuilder.equal(genreJoin.get(MangaGenre_.code), mangaFilter.getGenre())
//            );

            Join<Manga, MangaGenre> genreJoin =
                    root.join(Manga_.genres, JoinType.LEFT);
            if (!CollectionUtils.isEmpty(mangaFilter.getGenre())) {
//                Join<Manga, MangaGenre> genreJoin = root.join(Manga_.genres);

                predicates.add(
                        genreJoin.get(MangaGenre_.code).in(mangaFilter.getGenre())
                );
            }



            query.groupBy(root.get(Manga_.id));

            if (!CollectionUtils.isEmpty(mangaFilter.getGenreNotIn())) {

                Expression<Long> forbiddenCount =
                        criteriaBuilder.sum(
                                criteriaBuilder.<Long>selectCase()
                                        .when(
                                                genreJoin.get(MangaGenre_.code)
                                                        .in(mangaFilter.getGenreNotIn()),
                                                1L
                                        )
                                        .otherwise(0L)
                        );

                query.having(criteriaBuilder.equal(forbiddenCount, 0L));
            }

//            if (!CollectionUtils.isEmpty(mangaFilter.getGenreNotIn())) {
//
//                Subquery<Long> subquery = query.subquery(Long.class);
//                Root<Manga> subRoot = subquery.from(Manga.class);
//                Join<Manga, MangaGenre> subJoin = subRoot.join(Manga_.genres);
//
//                subquery.select(subRoot.get(Manga_.id))
//                        .where(
//                                criteriaBuilder.and(
//                                        criteriaBuilder.equal(subRoot.get(Manga_.id), root.get(Manga_.id)),
//                                        subJoin.get(MangaGenre_.code)
//                                                .in(mangaFilter.getGenreNotIn())
//                                )
//                        );
//
//                predicates.add(criteriaBuilder.not(criteriaBuilder.exists(subquery)));
//            }


            return concatenatePredicate(predicates, criteriaBuilder);
        });
    }


    public Predicate concatenatePredicate(List<Predicate> predicates, CriteriaBuilder cb) {
        int size = predicates.size();
        return size > 0 ? cb.and((Predicate[]) predicates.toArray(new Predicate[size])) : cb.conjunction();
    }
}
