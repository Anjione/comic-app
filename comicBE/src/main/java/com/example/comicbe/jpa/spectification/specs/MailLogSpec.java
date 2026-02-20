package com.example.comicbe.jpa.spectification.specs;

import com.example.comicbe.constant.CompareDate;
import com.example.comicbe.jpa.entity.EmailLog;
import com.example.comicbe.jpa.spectification.base.SearchPageSpecification;
import com.example.comicbe.payload.filter.MailLogFilter;
import com.example.comicbe.utils.DateTimeUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MailLogSpec extends SearchPageSpecification<MailLogFilter, EmailLog> {

    public MailLogSpec(MailLogFilter searchPage) {
        super(searchPage);
    }

    @Override
    public Predicate toPredicate(Root<EmailLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, MailLogFilter search) {
        List<Predicate> predicates = new ArrayList<>();


        if (search.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), search.getStatus()));
        }

        if (StringUtils.hasText(search.getEmailTo())) {
            predicates.add(criteriaBuilder.like(root.get("emailTo"), "%" + search.getEmailTo() + "%"));
        }

        if (StringUtils.hasText(search.getSubject())) {
            predicates.add(criteriaBuilder.like(root.get("subject"), "%" + search.getSubject() + "%"));
        }

        if (StringUtils.hasText(search.getContent())) {
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + search.getContent() + "%"));
        }

        if (search.getSentTime() != null) {
            if (search.getTypeQuery() != null) {
                queryByType(search, predicates, criteriaBuilder, root);
            } else {
                predicates.add(criteriaBuilder.equal(root.get("sentTime"), search.getSentTime()));

            }
        }

        if (search.getFromDateTime() != null && search.getToDateTime() != null) {
            predicates.add(criteriaBuilder.between(root.get("sentTime"), search.getFromDateTime(), search.getToDateTime()));

        }


        query.distinct(true);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void queryByType(MailLogFilter search, List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<EmailLog> root) {
        if (CompareDate.lookupForCode(search.getTypeQuery()) != null) {
            CompareDate compareDate = CompareDate.lookupForCode(search.getTypeQuery());
            LocalDateTime now = LocalDateTime.now();

            switch (compareDate) {
                case CompareDate.EQUALS: {
                    predicates.add(criteriaBuilder.equal(root.get("sentTime"), search.getSentTime()));
                    break;
                }

                case IN_HOURS: {
                    LocalDateTime startOfHour = now.truncatedTo(ChronoUnit.HOURS);
                    LocalDateTime endOfHour = startOfHour.plusHours(1).minusNanos(1);
                    predicates.add(criteriaBuilder.between(root.get("sentTime"), startOfHour, endOfHour));
                    break;
                }

                case IN_DAY: {
                    LocalDate today = LocalDate.now();

                    LocalDateTime startOfDay = today.atStartOfDay();
                    LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
                    predicates.add(criteriaBuilder.between(root.get("sentTime"), startOfDay, endOfDay));
                    break;
                }

                case IN_WEEK: {
                    LocalDate today = LocalDate.now();
                    LocalDateTime startOfWeek = DateTimeUtils.startOfWeek(today);
                    LocalDateTime endOfWeek = DateTimeUtils.endOfWeek(today);
                    predicates.add(criteriaBuilder.between(root.get("sentTime"), startOfWeek, endOfWeek));
                    break;

                }

                case IN_MONTH: {
                    LocalDate today = LocalDate.now();
                    LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
                    LocalDateTime endOfMonth = today.withDayOfMonth(today.lengthOfMonth()).atTime(LocalTime.MAX);
                    predicates.add(criteriaBuilder.between(root.get("sentTime"), startOfMonth, endOfMonth));
                    break;
                }

                case IN_YEAR: {
                    LocalDate today = LocalDate.now();
                    LocalDateTime startOfYear = today.withDayOfYear(1).atStartOfDay();
                    LocalDateTime endOfYear = today.withDayOfYear(today.lengthOfYear()).atTime(LocalTime.MAX);
                    predicates.add(criteriaBuilder.between(root.get("sentTime"), startOfYear, endOfYear));
                    break;
                }

                case AFTER_DATE: {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sentTime"), search.getSentTime()));
                }

                case BEFORE_DATE: {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sentTime"), search.getSentTime()));
                    break;
                }

                default: {
                    predicates.add(criteriaBuilder.equal(root.get("sentTime"), search.getSentTime()));
                    break;
                }


            }

        }
    }
}
