package com.example.comicbe.jpa.repository;

import com.example.comicbe.jpa.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("EmailTemplateRepository")
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> , JpaSpecificationExecutor<EmailTemplate> {
    Optional<EmailTemplate> findByTemplateCode(String templateCode);
}
