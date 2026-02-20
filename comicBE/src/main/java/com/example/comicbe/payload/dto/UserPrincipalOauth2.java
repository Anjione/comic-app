package com.example.comicbe.payload.dto;

import com.example.comicbe.jpa.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserPrincipalOauth2 implements UserDetails {

    private String username;
    @JsonIgnore
    private String password;
    private Long userId;
    private String email;

    private Boolean isPremium;
    private String avatar;
    private boolean requiredChangePass;
    private Integer freeTrialSpeakingTimes;
    private Integer freeTrialWritingTimes;
    private Integer freeTrialListeningTimes;
    private Integer freeTrialReadingTimes;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipalOauth2(String username, String password, String email, Long userId, Collection<? extends GrantedAuthority> authorities, Boolean isPremium, String avatar, boolean requiredChangePass
            , Integer freeTrialSpeakingTimes, Integer freeTrialWritingTimes, Integer freeTrialListeningTimes, Integer freeTrialReadingTimes) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.email = email;
        this.authorities = authorities;
        this.isPremium = isPremium;
        this.avatar = avatar;
        this.requiredChangePass = requiredChangePass;
        this.freeTrialSpeakingTimes = freeTrialSpeakingTimes;
        this.freeTrialWritingTimes = freeTrialWritingTimes;
        this.freeTrialListeningTimes = freeTrialListeningTimes;
        this.freeTrialReadingTimes = freeTrialReadingTimes;
    }

    // get roles and set authorities
    public static UserPrincipalOauth2 createPrincipalOauth2(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(roleEntity ->
                new SimpleGrantedAuthority("ROLE_" + roleEntity.getName())
        ).collect(Collectors.toList());

        return new UserPrincipalOauth2(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getId(),
                authorities,
                user.getPremiumExpireTime() != null && (user.getPremiumExpireTime().isAfter(LocalDateTime.now()) || user.getPremiumExpireTime().equals(LocalDateTime.now())),
                user.getAvatar(),
                user.isRequiredChangePass(),
                user.getFreeTrialSpeakingTimes(),
                user.getFreeTrialWritingTimes(),
                user.getFreeTrialListeningTimes(),
                user.getFreeTrialReadingTimes()
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}