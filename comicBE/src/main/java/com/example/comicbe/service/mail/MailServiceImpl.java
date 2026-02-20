package com.example.comicbe.service.mail;


import com.example.comicbe.constant.AppConstant;
import com.example.comicbe.constant.EmailLogStatus;
import com.example.comicbe.constant.SystemStatus;
import com.example.comicbe.jpa.entity.EmailLog;
import com.example.comicbe.jpa.entity.EmailTemplate;
import com.example.comicbe.jpa.entity.User;
import com.example.comicbe.jpa.repository.EmailLogRepository;
import com.example.comicbe.jpa.repository.EmailTemplateRepository;
import com.example.comicbe.jpa.repository.UserRepository;
import com.example.comicbe.jpa.spectification.specs.MailLogSpec;
import com.example.comicbe.jpa.spectification.specs.MailTemplateSpec;
import com.example.comicbe.jpa.spectification.specs.UserSpec;
import com.example.comicbe.payload.dto.mail.*;
import com.example.comicbe.payload.filter.MailLogFilter;
import com.example.comicbe.payload.filter.MailTemplateFilter;
import com.example.comicbe.payload.filter.UserFilter;
import com.example.comicbe.service.MailService;
import com.example.comicbe.utils.AppException;
import com.example.comicbe.utils.ErrorCode;
import com.example.comicbe.utils.LoggingUtils;
import com.example.comicbe.utils.response.PageList;
import com.example.comicbe.utils.validator.EmailTemplateValidator;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.comicbe.constant.SendEmailType.*;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier(value = "velocityEngine")
    private VelocityEngine velocityEngine;

    @Autowired
    private EmailTemplateRepository templateRepository;

    @Autowired
    private EmailTemplateValidator emailTemplateValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailLogRepository emailLogRepository;

//    @PostConstruct
//    public void init() {
//        Properties props = new Properties();
//        props.setProperty("resource.loader", "class");
//        props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//
//        velocityEngine = new VelocityEngine(props);
//        velocityEngine.init();
//    }

    @Override
    public PageList<MailTemplateDTO> findAll(MailTemplateFilter filter) {
        log.info("findAll MailTemplate with filter: {}", LoggingUtils.objToStringIgnoreEx(filter));
        MailTemplateSpec spec = new MailTemplateSpec(filter);
        Page<EmailTemplate> emailTemplates = templateRepository.findAll(spec, spec.getPageable());
        List<MailTemplateDTO> mailTemplateDTOS = emailTemplates.stream().map(emailTemplate -> {
            MailTemplateDTO mailTemplateDTO = new MailTemplateDTO();
            BeanUtils.copyProperties(emailTemplate, mailTemplateDTO);
            return mailTemplateDTO;
        }).toList();


        return PageList.<MailTemplateDTO>builder()
                .list(mailTemplateDTOS)
                .currentPage(emailTemplates.getPageable().getPageNumber() + 1)
                .totalRecord(emailTemplates.getTotalElements())
                .pageSize(emailTemplates.getPageable().getPageSize())
                .success(true)
                .totalPage(emailTemplates.getTotalPages())
                .build();
    }

    @Override
    public PageList<EmailLogDTO> findAllMailLog(MailLogFilter filter) {
        log.info("findAll MailLog with filter: {}", LoggingUtils.objToStringIgnoreEx(filter));
        MailLogSpec spec = new MailLogSpec(filter);
        Page<EmailLog> emailLogs = emailLogRepository.findAll(spec, spec.getPageable());
        List<EmailLogDTO> emailLogDTOS = emailLogs.stream().map(emailLog -> {
            EmailLogDTO emailLogDTO = new EmailLogDTO();
            BeanUtils.copyProperties(emailLog, emailLogDTO);
            return emailLogDTO;
        }).toList();


        return PageList.<EmailLogDTO>builder()
                .list(emailLogDTOS)
                .currentPage(emailLogs.getPageable().getPageNumber() + 1)
                .totalRecord(emailLogs.getTotalElements())
                .pageSize(emailLogs.getPageable().getPageSize())
                .success(true)
                .totalPage(emailLogs.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public void sendEmailOnManagement(ManagementSendEmailRequest request) {
        log.info("sendEmailOnManagement with body: {}", LoggingUtils.objToStringIgnoreEx(request));
        List<String> emails = new ArrayList<>();
        switch (request.getType()) {
            case ALL_USER ->
                    emails = userRepository.findAllByEnabled(Boolean.TRUE).stream().map(User::getEmail).collect(Collectors.toList());
            case USER_PURCHASED ->
                    emails = userRepository.findAllByEnabledAndPremiumExpireTimeGreaterThanEqual(Boolean.TRUE, LocalDateTime.now()).stream().map(User::getEmail).collect(Collectors.toList());
            case USER_NOT_PURCHASED ->
                    emails = userRepository.findAllByEnabledAndPremiumExpireTimeLessThan(Boolean.TRUE, LocalDateTime.now()).stream().map(User::getEmail).collect(Collectors.toList());
            case OTHER -> emails = new ArrayList<>(request.getToAddress());
            default -> throw new IllegalStateException("Unexpected value: " + request.getType());
        }
        List<Future<EmailLog>> futures = new ArrayList<>();
        List<EmailLog> emailLogs = new ArrayList<>();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (String email : emails) {
                futures.add(executorService.submit(() -> this.sendMailWithTemplate(email, request.getSubject(), request.getContent())));
            }

            for (Future<EmailLog> future : futures) {
                emailLogs.add(future.get());
            }
        } catch (Exception ex) {
            log.error("sendEmailOnManagement has error: {} - stackTrace: {}", ex.getMessage(), ex.getStackTrace());
        }
        log.info("save data email log to DB");
        emailLogRepository.saveAllAndFlush(emailLogs);
    }

    @Override
    public void sendEmailWithTemplate(String to, String subject, Map<String, Object> variables) {
        // Load template
        Template template = velocityEngine.getTemplate("src/main/resources/templates/email-template.vm", "UTF-8");

        // Merge data into template
        VelocityContext context = new VelocityContext(variables);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String htmlContent = writer.toString();
        try {
            this.sendMailWithTemplate(to, subject, htmlContent);
        } catch (Exception e) {
            throw new AppException(ErrorCode.SENT_EMAIL_ERROR);
        }
    }

    @Override
    public MailTemplateDTO createMailTemplate(MailTemplateRequest mailTemplateRequest) {
        emailTemplateValidator.validateDuplicate(mailTemplateRequest);
        EmailTemplate emailTemplate = new EmailTemplate();
        BeanUtils.copyProperties(mailTemplateRequest, emailTemplate);
        emailTemplate.setStatus(SystemStatus.ACTIVE.getStatus());
        emailTemplate.setIsConfig(Boolean.FALSE);
        emailTemplate = templateRepository.saveAndFlush(emailTemplate);
        MailTemplateDTO mailTemplateDTO = new MailTemplateDTO();
        BeanUtils.copyProperties(emailTemplate, mailTemplateDTO);
        return mailTemplateDTO;
    }

    @Override
    public MailTemplateDTO updateMailTemplate(MailTemplateRequest mailTemplateRequest) {
        EmailTemplate emailTemplate = templateRepository.findByTemplateCode(mailTemplateRequest.getTemplateCode()).orElseThrow(() -> new AppException(ErrorCode.EMAIL_TEMPLATE_NOT_FOUND));
        Optional<EmailTemplate> optional = templateRepository.findByTemplateCode(mailTemplateRequest.getTemplateCode());
        if (optional.isPresent()) {
            if (!optional.get().getId().equals(emailTemplate.getId())) {
                throw new AppException(ErrorCode.EMAIL_TEMPLATE_NOT_VALID);
            }
        }
        BeanUtils.copyProperties(mailTemplateRequest, emailTemplate);
        emailTemplate = templateRepository.save(emailTemplate);
        MailTemplateDTO mailTemplateDTO = new MailTemplateDTO();
        BeanUtils.copyProperties(emailTemplate, mailTemplateDTO);
        return mailTemplateDTO;
    }

    @Override
    public void sendEmailWithSchedule() {
        LocalDateTime now = LocalDateTime.now();
        UserFilter filter = UserFilter.builder()
                .createdDateFrom(now.minusDays(7))
                .createdDateTo(now)
                .enabled(Boolean.TRUE)
                .build();
        UserSpec spec = new UserSpec(filter);

        List<User> users = userRepository.findAll(spec);

        List<SendMailRequest> sendMailRequests = users.stream().filter(item -> !item.getEmail().equalsIgnoreCase("NO_VALUE")).map(user -> {
            String templateCode = null;
            int days = Integer.parseInt(ChronoUnit.DAYS.between(user.getCreatedDate(), now) + "");
            switch (days) {
                case 1 ->
                        templateCode = user.getPremiumExpireTime() != null && (user.getPremiumExpireTime().isAfter(LocalDateTime.now()) || user.getPremiumExpireTime().equals(LocalDateTime.now())) ? AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_ONE_PURCHASED : AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_ONE_NOT_PURCHASED;
                case 2 ->
                        templateCode = user.getPremiumExpireTime() != null && (user.getPremiumExpireTime().isAfter(LocalDateTime.now()) || user.getPremiumExpireTime().equals(LocalDateTime.now())) ? AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_TWO_PURCHASED : AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_TWO_NOT_PURCHASED;
                case 3 ->
                        templateCode = user.getPremiumExpireTime() != null && (user.getPremiumExpireTime().isAfter(LocalDateTime.now()) || user.getPremiumExpireTime().equals(LocalDateTime.now())) ? AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_THREE_PURCHASED : AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_THREE_NOT_PURCHASED;
                case 4 ->
                        templateCode = user.getPremiumExpireTime() != null && (user.getPremiumExpireTime().isAfter(LocalDateTime.now()) || user.getPremiumExpireTime().equals(LocalDateTime.now())) ? AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_FOUR_PURCHASED : AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_FOUR_NOT_PURCHASED;
                case 5 ->
                        templateCode = user.getPremiumExpireTime() != null && (user.getPremiumExpireTime().isAfter(LocalDateTime.now()) || user.getPremiumExpireTime().equals(LocalDateTime.now())) ? AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_FIVE_PURCHASED : AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_FIVE_NOT_PURCHASED;
                case 6 ->
                        templateCode = user.getPremiumExpireTime() != null && (user.getPremiumExpireTime().isAfter(LocalDateTime.now()) || user.getPremiumExpireTime().equals(LocalDateTime.now())) ? AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_SIX_PURCHASED : AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_SIX_NOT_PURCHASED;
                case 7 ->
                        templateCode = user.getPremiumExpireTime() != null && (user.getPremiumExpireTime().isAfter(LocalDateTime.now()) || user.getPremiumExpireTime().equals(LocalDateTime.now())) ? AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_SEVEN_PURCHASED : AppConstant.TEMPLATE_CODE_EMAIL_DAYS.DAY_SEVEN_NOT_PURCHASED;
            }

            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("username", user.getUsername());
            return SendMailRequest.builder()
                    .templateCode(templateCode)
                    .toAddress(user.getEmail())
                    .subject("Email Auto")
                    .bodyMap(bodyMap)
                    .build();
        }).toList();

        AtomicInteger counter = new AtomicInteger();
        Collection<List<SendMailRequest>> subList = sendMailRequests.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 5))
                .values();

        for (List<SendMailRequest> batch : subList) {
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                for (SendMailRequest request : batch) {
                    scope.fork(() -> {
                        this.sendEmail(request);
                        return null;
                    });
                }
                scope.join().throwIfFailed();
            } catch (Exception ex) {
                log.error("sendEmailWithSchedule has error: {}, stackTrace: {}", ex.getMessage(), ex.getStackTrace());
            }
        }
    }

    @Override
    public MailTemplateDTO getById(Long id) {
        EmailTemplate emailTemplate = templateRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EMAIL_TEMPLATE_NOT_FOUND));
        MailTemplateDTO mailTemplateDTO = new MailTemplateDTO();
        BeanUtils.copyProperties(emailTemplate, mailTemplateDTO);
        return mailTemplateDTO;
    }

    @Override
    public Object delete(List<Long> ids) {
        List<EmailTemplate> emailTemplates = templateRepository.findAllById(ids);
        templateRepository.deleteAll(emailTemplates);
        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    public MailTemplateDTO actionById(Long id, int action) {
        EmailTemplate emailTemplate = templateRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EMAIL_TEMPLATE_NOT_FOUND));
        SystemStatus systemStatus = SystemStatus.lookupForCode(action);
        if (systemStatus == null) {
            throw new AppException(ErrorCode.EMAIL_ACTION_NOT_FOUND);
        }
        emailTemplate.setStatus(systemStatus.getStatus());
        emailTemplate = templateRepository.saveAndFlush(emailTemplate);
        MailTemplateDTO mailTemplateDTO = new MailTemplateDTO();
        BeanUtils.copyProperties(emailTemplate, mailTemplateDTO);
        return mailTemplateDTO;
    }


    @Override
    @Transactional
    public void sendEmail(SendMailRequest mailRequest) {
        EmailTemplate template = templateRepository.findByTemplateCode(mailRequest.getTemplateCode())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_TEMPLATE_NOT_FOUND));

        Set<String> templateKey = Stream.of(template.getTemplateKey().split(",")).collect(Collectors.toSet());
        Map<String, Object> dataSendMail = new HashMap<>();
        convertListToMap(templateKey, dataSendMail, mailRequest.getBodyMap());

        String templateContent = template.getContent();
        VelocityContext context = new VelocityContext(dataSendMail);

        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "sendEmail", templateContent);
        try {
            EmailLog emailLog = this.sendMailWithTemplate(mailRequest.getToAddress(), mailRequest.getSubject(), writer.toString());
            emailLogRepository.saveAndFlush(emailLog);
        } catch (Exception e) {
            throw new AppException(ErrorCode.SENT_EMAIL_ERROR);
        }
    }

    private void convertListToMap(Set<String> data, Map<String, Object> dataSendMail, Map<String, String> dataSendMailOrigin) {
        data.stream().parallel().forEach(s -> {
            if (dataSendMailOrigin.containsKey(s)) {
                dataSendMail.put(s, dataSendMailOrigin.get(s));
            }
        });
    }

    private EmailLog sendMailWithTemplate(String to, String subject, String content) {
        EmailLog emailLog = new EmailLog();
        emailLog.setEmailTo(to);
        emailLog.setSubject(subject);
        emailLog.setContent(content);
        emailLog.setSentTime(LocalDateTime.now());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            emailLog.setStatus(EmailLogStatus.SUCCESS);
        } catch (Exception e) {
            log.error("sendMailWithTemplate has error: {} - stackTrace: {}", e.getMessage(), e.getStackTrace());
            emailLog.setStatus(EmailLogStatus.FAILED);
        }
        return emailLog;
    }
}
