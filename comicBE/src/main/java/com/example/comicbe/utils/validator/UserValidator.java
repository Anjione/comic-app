package com.example.comicbe.utils.validator;

import com.example.comicbe.constant.AppConstant;
import com.example.comicbe.jpa.entity.User;
import com.example.comicbe.jpa.repository.UserRepository;
import com.example.comicbe.payload.dto.user.RegisterDTO;
import com.example.comicbe.utils.AppException;
import com.example.comicbe.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    public User validateExists(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public User existsUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    public User existsUsernameOrEmailActive(String username) {
        User user = userRepository.findByEmailOrUsernameAndEnabled(username, username, AppConstant.ACTIVE.ACTIVE_STATUS);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    public User existsUsernameOrEmail(String username) {
        return userRepository.findByEmailOrUsernameAndEnabled(username, username, AppConstant.ACTIVE.ACTIVE_STATUS);
    }

    public void validateDuplicateEmailOrUserName(RegisterDTO registerDTO) {
        User user = userRepository.findByEmailOrUsernameAndEnabled(registerDTO.getEmail(), registerDTO.getEmail(), AppConstant.ACTIVE.ACTIVE_STATUS);
        if (user != null) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    public User validateConfirmUser(String tokenConfirm) {
        Optional<User> userOptional = userRepository.findByConfirmRegisterToken(tokenConfirm);
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        User user = userOptional.get();
        if (user.getExpiredRegisterToken().isBefore(LocalDateTime.now())) {
            user.removeRoles();
            userRepository.delete(user);
            throw new AppException(ErrorCode.EMAIL_CONFIRM_TIME_RANGE_OVER);
        }
        return user;
    }
}
