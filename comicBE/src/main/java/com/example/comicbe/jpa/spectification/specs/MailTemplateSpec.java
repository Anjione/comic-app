package com.example.comicbe.jpa.spectification.specs;

import com.example.comicbe.jpa.entity.EmailTemplate;
import com.example.comicbe.jpa.spectification.base.SearchPageSpecification;
import com.example.comicbe.payload.filter.MailTemplateFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MailTemplateSpec extends SearchPageSpecification<MailTemplateFilter, EmailTemplate> {

    public MailTemplateSpec(MailTemplateFilter searchPage) {
        super(searchPage);
    }

    @Override
    public Predicate toPredicate(Root<EmailTemplate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, MailTemplateFilter search) {
        List<Predicate> predicates = new ArrayList<>();

        if (search.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), search.getId()));
        }

        if (search.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), search.getStatus()));
        }

        if (StringUtils.hasText(search.getTemplateName())) {
            predicates.add(criteriaBuilder.like(root.get("templateName"), "%" + search.getTemplateName() + "%"));
        }

        if (StringUtils.hasText(search.getTemplateCode())) {
            predicates.add(criteriaBuilder.like(root.get("templateCode"), "%" + search.getTemplateCode() + "%"));
        }

        if (StringUtils.hasText(search.getTemplateKey())) {
            predicates.add(criteriaBuilder.like(root.get("templateKey"), "%" + search.getTemplateKey() + "%"));
        }

        if (StringUtils.hasText(search.getType())) {
            predicates.add(criteriaBuilder.like(root.get("type"), search.getType()));
        }

        query.distinct(true);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}