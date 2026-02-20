package com.example.comicbe.service;


import com.example.comicbe.jpa.entity.User;
import com.example.comicbe.jpa.repository.UserRepository;
import com.example.comicbe.jpa.spectification.specs.UserSpec;
import com.example.comicbe.payload.dto.UserPrincipalOauth2;
import com.example.comicbe.payload.filter.UserFilter;
import com.example.comicbe.utils.AppException;
import com.example.comicbe.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userCustomService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSpec userSpec = new UserSpec(UserFilter.builder().userLogged(username).build());
        User userEntity = userRepository.findOne(userSpec).orElse(null);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!userEntity.isEnabled() && (userEntity.getRequiredConfirmEmail() != null && userEntity.getRequiredConfirmEmail())) {
            throw new AppException(ErrorCode.USER_REQUIRED_CONFIRM_EMAIL);
        }

        if (!userEntity.isEnabled()) {
            throw new AppException(ErrorCode.USER_LOCKED);
        }

        return UserPrincipalOauth2.createPrincipalOauth2(userEntity);
    }
}