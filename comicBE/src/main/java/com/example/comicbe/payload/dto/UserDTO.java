package com.example.comicbe.payload.dto;

import com.example.comicbe.constant.AppConstant;
import com.example.comicbe.jpa.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends BaseDTO {
    private String fullName;
    private String username;
    private String email;
    private String avatar;
    private boolean enabled;
    private Integer freeTrialTimes;

    private List<String> roles = new ArrayList<>();

    @JsonFormat(pattern = AppConstant.DATE.FORMAT_DATE_RESPONSE, timezone = AppConstant.DATE.TIMEZONE_ICT)
    private LocalDateTime premiumExpireTime;

    private Boolean isPremium;

    public void addRoles(Set<Role> roles) {
        this.roles.clear();
        this.roles.addAll(roles.stream().map(Role::getName).toList());
    }

    public void preparePremium() {
        this.isPremium = this.premiumExpireTime != null && (this.premiumExpireTime.isAfter(LocalDateTime.now()) || this.premiumExpireTime.equals(LocalDateTime.now()));
    }
}
