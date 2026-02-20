package com.example.comicbe.payload.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthResponse {
    private Long id;
    private String username;
    private String email;
    private Boolean isPremium;
    private String avatar;
    private boolean requiredChangePass;
    private Integer freeTrialSpeakingTimes;
    private Integer freeTrialWritingTimes;
    private Integer freeTrialListeningTimes;
    private Integer freeTrialReadingTimes;
    private Collection<? extends GrantedAuthority> roles;
}
