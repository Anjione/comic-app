package com.example.comicbe.service;


import com.example.comicbe.jpa.entity.User;
import com.example.comicbe.payload.dto.UserAuthResponse;
import com.example.comicbe.payload.dto.UserDTO;
import com.example.comicbe.payload.dto.user.*;
import com.example.comicbe.payload.filter.UserFilter;
import com.example.comicbe.utils.response.PageList;

import java.util.List;

public interface UserService {
    List<UserDTO> findByIds(List<Long> userIds);

    Object register(RegisterDTO registerDTO);

    Object confirm(String tokenConfirm);

    UserAuthResponse changeInformation(ChangeInformationDTO changeInformationDTO);

    Object changePassword(ChangePasswordDTO changePasswordDTO);

    Object resetPassword(ResetPasswordDTO resetPasswordDTO);

    User saveInternalData(CreateUserRequest request);

    UserDTO saveRequest(CreateUserRequest request);

    UserDTO updateRequest(Long userId, CreateUserRequest request);

    User existsWithSocialId(String socialId);

    UserDTO convertFromEntity(User user);

    List<UserDTO> findAllWithOutPage(UserFilter filter);

    PageList<UserDTO> findAll(UserFilter filter);

    Object lockUnlock(Long userId);

    Object resetPass(Long userId);

    Object setPremium(Long userId, PremiumDTO premiumDTO);

    Object resetPremium(Long userId);

    Object scheduleClearTrialDaily();
}
