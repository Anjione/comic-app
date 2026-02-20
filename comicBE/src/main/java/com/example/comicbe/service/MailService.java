package com.example.comicbe.service;


import com.example.comicbe.payload.dto.mail.*;
import com.example.comicbe.payload.filter.MailLogFilter;
import com.example.comicbe.payload.filter.MailTemplateFilter;
import com.example.comicbe.utils.response.PageList;

import java.util.List;
import java.util.Map;

public interface MailService {
    PageList<MailTemplateDTO> findAll(MailTemplateFilter filter);

    PageList<EmailLogDTO> findAllMailLog(MailLogFilter filter);

    void sendEmailOnManagement(ManagementSendEmailRequest request);

    void sendEmailWithTemplate(String to, String subject, Map<String, Object> variables);

    MailTemplateDTO getById(Long id);

    Object delete(List<Long> id);

    MailTemplateDTO actionById(Long id, int action);

    void sendEmail(SendMailRequest mailRequest);

    MailTemplateDTO createMailTemplate(MailTemplateRequest mailTemplateRequest);

    MailTemplateDTO updateMailTemplate(MailTemplateRequest mailTemplateRequest);

    void sendEmailWithSchedule();
}
