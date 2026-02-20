package com.example.comicbe.service.auth;

import com.example.comicbe.config.RsaKeyConfigProperties;
import com.example.comicbe.constant.AppConstant;
import com.example.comicbe.jpa.entity.Role;
import com.example.comicbe.jpa.entity.User;
import com.example.comicbe.jpa.repository.RoleRepository;
import com.example.comicbe.jpa.repository.UserRepository;
import com.example.comicbe.jpa.spectification.specs.UserSpec;
import com.example.comicbe.payload.dto.UserAuthResponse;
import com.example.comicbe.payload.dto.UserPrincipalOauth2;
import com.example.comicbe.payload.dto.mail.SendMailRequest;
import com.example.comicbe.payload.dto.user.*;
import com.example.comicbe.payload.filter.UserFilter;
import com.example.comicbe.service.MailService;
import com.example.comicbe.service.UserService;
import com.example.comicbe.utils.*;
import com.example.comicbe.utils.response.PageList;
//import com.example.comicbe.utils.validator.ProductValidator;
import com.example.comicbe.utils.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.example.comicbe.payload.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RsaKeyConfigProperties rsaKeyConfigProperties;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CurrentUser currentUser;
//
//    @Autowired
//    private ProductValidator productValidator;

    @Override
    public List<UserDTO> findByIds(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        return users.stream().map(item -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(item, userDTO);
            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Object register(RegisterDTO registerDTO) {
        String emailDetect = RsaUtil.decrypt(registerDTO.getCaptchaToken(), rsaKeyConfigProperties.privateKey());
        if (!emailDetect.equals(registerDTO.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_NOT_MATCH);
        }
        String pass = RsaUtil.decrypt(registerDTO.getPassword(), rsaKeyConfigProperties.privateKey());
        userValidator.validateDuplicateEmailOrUserName(registerDTO);
        List<Role> roles = roleRepository.findByName("CUSTOMER");
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        user.setPassword(passwordEncoder.encode(pass));
        user.setConfirmRegisterToken(UUID.randomUUID().toString());
        user.setExpiredRegisterToken(LocalDateTime.now().plusMinutes(10));
        user.setEnabled(Boolean.FALSE);
        user.setRequiredConfirmEmail(Boolean.TRUE);
        user.addRoles(roles);
        user = userRepository.saveAndFlush(user);
        Map<String, String> bodyMap = this.prepareDataMapping(user, registerDTO);
        mailService.sendEmail(SendMailRequest.builder()
                        .templateCode(AppConstant.TEMPLATE_EMAIL.REGISTER_USER)
                        .toAddress(registerDTO.getEmail())
                        .subject("Ielts Advanced Register Account")
                        .bodyMap(bodyMap)
                .build());
        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    public Object confirm(String tokenConfirm) {
        String token = RsaUtil.decrypt(tokenConfirm, rsaKeyConfigProperties.privateKey());
        User user = userValidator.validateConfirmUser(token);
        user.setEnabled(Boolean.TRUE);
        user.setConfirmRegisterToken(null);
        user.setExpiredRegisterToken(null);
        user.setRequiredConfirmEmail(null);
        userRepository.saveAndFlush(user);
        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    @Transactional
    public UserAuthResponse changeInformation(ChangeInformationDTO changeInformationDTO) {
        User user = userValidator.validateExists(currentUser.currentUser().getUserId());
        if (StringUtils.hasText(changeInformationDTO.getAvatar())) {
            user.setAvatar(changeInformationDTO.getAvatar());
        }
        if (!StringUtils.hasText(changeInformationDTO.getFullName())) {
            user.setFullName(changeInformationDTO.getFullName());
        }
        user = userRepository.saveAndFlush(user);
        UserPrincipalOauth2 userPrincipalOauth2 = UserPrincipalOauth2.createPrincipalOauth2(user);
        return UserAuthResponse.builder()
                .email(userPrincipalOauth2.getEmail())
                .username(userPrincipalOauth2.getUsername())
                .id(userPrincipalOauth2.getUserId())
                .roles(userPrincipalOauth2.getAuthorities())
                .isPremium(userPrincipalOauth2.getIsPremium())
                .avatar(userPrincipalOauth2.getAvatar())
                .build();
    }

    @Override
    @Transactional
    public Object changePassword(ChangePasswordDTO changePasswordDTO) {
        String oldPass = RsaUtil.decrypt(changePasswordDTO.getPasswordOld(), rsaKeyConfigProperties.privateKey());
        String newPass = RsaUtil.decrypt(changePasswordDTO.getNewPassword(), rsaKeyConfigProperties.privateKey());
        String confirmNewPass = RsaUtil.decrypt(changePasswordDTO.getConfirmNewPassword(), rsaKeyConfigProperties.privateKey());

        User user = userValidator.validateExists(currentUser.currentUser().getUserId());

        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new AppException(ErrorCode.OLD_PASS_NOT_MATCH);
        }

        if (!newPass.equals(confirmNewPass)) {
            throw new AppException(ErrorCode.CONFIRM_PASS_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(newPass));
        user.setRequiredChangePass(Boolean.FALSE);
        userRepository.saveAndFlush(user);

        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    @Transactional
    public Object resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userValidator.existsUsernameOrEmailActive(resetPasswordDTO.getEmail());
        String newPass = PasswordGenerator.generate(12);
        user.setPassword(passwordEncoder.encode(newPass));
        user.setRequiredChangePass(Boolean.TRUE);
        user = userRepository.saveAndFlush(user);
        Map<String, String> bodyMap = this.prepareDataResetPassMapping(newPass, user);
        mailService.sendEmail(SendMailRequest.builder()
                .templateCode(AppConstant.TEMPLATE_EMAIL.RESET_PASSWORD)
                .toAddress(resetPasswordDTO.getEmail())
                .subject("Ielts Advanced Reset Account")
                .bodyMap(bodyMap)
                .build());
        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    @Transactional
    public User saveInternalData(CreateUserRequest request) {
        User user = userValidator.existsUsernameOrEmail(request.getUsername());
        List<Role> roles = roleRepository.findByNameIn(request.getRoleNames());
        if (user == null) {
            user = new User();
            BeanUtils.copyProperties(request, user, "password");
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.addRoles(roles);
            user.setSocialIdLogin(request.getSocialIdLogin());
            user = userRepository.saveAndFlush(user);
        }
        return user;
    }

    @Override
    @Transactional
    public UserDTO saveRequest(CreateUserRequest request) {
        String pass = PasswordGenerator.generate(12);
        if (!StringUtils.hasText(request.getPassword())) {
            request.setPassword(pass);
        }
        request.setEnabled(Boolean.TRUE);
        User user = this.saveInternalData(request);
        request.setRequiredChangePass(Boolean.TRUE);
        Map<String, String> parameter = this.prepareDataSaveUser(user, pass);
        mailService.sendEmail(SendMailRequest.builder()
                .templateCode(AppConstant.TEMPLATE_EMAIL.SAVE_USER)
                .toAddress(user.getEmail())
                .subject("Ielts Advanced Added Account")
                .bodyMap(parameter)
                .build());
        return this.convertFromEntity(user);
    }

    @Override
    @Transactional
    public UserDTO updateRequest(Long userId, CreateUserRequest request) {
        User user = userValidator.validateExists(userId);
        if (StringUtils.hasText(request.getFullName())) {
            user.setFullName(request.getFullName());
        }

        if (StringUtils.hasText(request.getAvatar())) {
            user.setAvatar(request.getAvatar());
        }

        if (!CollectionUtils.isEmpty(request.getRoleNames())) {
            List<Role> roles = roleRepository.findByNameIn(request.getRoleNames());
            user.addRoles(roles);
        }
        user = userRepository.saveAndFlush(user);
        return this.convertFromEntity(user);
    }

    @Override
    public User existsWithSocialId(String socialId) {
        return userRepository.findBySocialIdLoginAndEnabled(socialId, AppConstant.ACTIVE.ACTIVE_STATUS);
    }

    @Override
    public UserDTO convertFromEntity(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        dto.preparePremium();
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            dto.addRoles(user.getRoles());
        }
        return dto;
    }

    @Override
    public List<UserDTO> findAllWithOutPage(UserFilter filter) {
        log.info("findAllWithOutPage Users with filter: {}", LoggingUtils.objToStringIgnoreEx(filter));
        UserSpec userSpec = new UserSpec(filter);
        List<User> users = userRepository.findAll(userSpec);
        return users.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }

    @Override
    public PageList<UserDTO> findAll(UserFilter filter) {
        log.info("findAll Users with filter: {}", LoggingUtils.objToStringIgnoreEx(filter));
        UserSpec userSpec = new UserSpec(filter);
        Page<User> page = userRepository.findAll(userSpec, userSpec.getPageable());
        List<UserDTO> userDTOS = new ArrayList<>();
        try {
            userDTOS = page.getContent().stream().map(this::convertFromEntity).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("findAll Users error: {} - stackTrace: {}", ex.getMessage(), ex.getStackTrace());
        }
        return PageList.<UserDTO>builder()
                .list(userDTOS)
                .currentPage(page.getPageable().getPageNumber() + 1)
                .totalRecord(page.getTotalElements())
                .pageSize(page.getPageable().getPageSize())
                .success(true)
                .totalPage(page.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public Object lockUnlock(Long userId) {
        User user = userValidator.validateExists(userId);
        user.setEnabled(!user.isEnabled());
        userRepository.saveAndFlush(user);
        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    @Transactional
    public Object resetPass(Long userId) {
        String pass = PasswordGenerator.generate(12);
        User user = userValidator.validateExists(userId);
        user.setPassword(passwordEncoder.encode(pass));
        user.setRequiredChangePass(Boolean.TRUE);
        userRepository.saveAndFlush(user);

        Map<String, String> bodyMap = this.prepareDataResetPassMapping(pass, user);
        mailService.sendEmail(SendMailRequest.builder()
                .templateCode(AppConstant.TEMPLATE_EMAIL.RESET_PASSWORD)
                .toAddress(user.getEmail())
                .subject("Ielts Advanced Reset Account")
                .bodyMap(bodyMap)
                .build());
        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    @Transactional
    public Object setPremium(Long userId, PremiumDTO premiumDTO) {
//        User user = userValidator.validateExists(userId);
//        Product product = productValidator.validatorExists(premiumDTO.getProductId());
//        user.addDaysPremium(product.getDaysActive());
//        userRepository.saveAndFlush(user);
        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    @Transactional
    public Object resetPremium(Long userId) {
        User user = userValidator.validateExists(userId);
        user.setPremiumExpireTime(null);
        userRepository.saveAndFlush(user);
        return AppConstant.RESPONSE.SUCCESS;
    }

    @Override
    @Transactional
    public Object scheduleClearTrialDaily() {
        List<User> users = userRepository.findAllByEnabled(Boolean.TRUE);
        users = users.stream().peek(item -> {
            item.setFreeTrialListeningTimes(0);
            item.setFreeTrialSpeakingTimes(0);
            item.setFreeTrialReadingTimes(0);
            item.setFreeTrialWritingTimes(0);
        }).collect(Collectors.toList());
        userRepository.saveAllAndFlush(users);

        return AppConstant.RESPONSE.SUCCESS;
    }

    private Map<String, String> prepareDataMapping(User user, RegisterDTO registerDTO) {
        Map<String, String> result = new HashMap<>();
        result.put("name", user.getFullName());
        result.put("message", "Chào mừng bạn đến với IELTS ADVANCED hãy click link bên dưới để xác nhận tài khoản");
        result.put("buttonUrl", registerDTO.getRouterConfirm() + "?token=" + RsaUtil.encrypt(user.getConfirmRegisterToken(), rsaKeyConfigProperties.publicKey()));
        result.put("buttonText", "Xác nhận ngay");
        return result;
    }

    private Map<String, String> prepareDataResetPassMapping(String newPass, User user) {
        Map<String, String> result = new HashMap<>();
        result.put("name", user.getFullName());
        result.put("newPass", newPass);
        return  result;
    }

    private Map<String, String> prepareDataSaveUser(User user, String pass) {
        Map<String, String> result = new HashMap<>();
        result.put("name", user.getFullName());
        result.put("username", user.getUsername());
        result.put("password", pass);
        return  result;
    }
}
