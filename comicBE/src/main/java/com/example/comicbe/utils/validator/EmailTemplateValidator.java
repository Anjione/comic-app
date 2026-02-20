package com.example.comicbe.utils.validator;


import com.example.comicbe.jpa.entity.EmailTemplate;
import com.example.comicbe.jpa.repository.EmailTemplateRepository;
import com.example.comicbe.payload.dto.mail.MailTemplateRequest;
import com.example.comicbe.utils.AppException;
import com.example.comicbe.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmailTemplateValidator {

    @Autowired
    private EmailTemplateRepository templateRepository;

    public void validateDuplicate(MailTemplateRequest request) {
        Optional<EmailTemplate> template = templateRepository.findByTemplateCode(request.getTemplateCode());
        if (template.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_TEMPLATE_DUPLICATE);
        }
    }
}
