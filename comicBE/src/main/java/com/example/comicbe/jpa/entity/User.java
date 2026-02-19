package com.example.comicbe.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "required_confirm_email")
    private Boolean requiredConfirmEmail;

    @Column(name = "required_change_pass", nullable = false)
    private boolean requiredChangePass = false;

    @Column(name = "premium_expire_time")
    private LocalDateTime premiumExpireTime;

    @Column(name = "confirm_register_token")
    private String confirmRegisterToken;

    @Column(name = "expired_register_token")
    private LocalDateTime expiredRegisterToken;

    @Column(name = "social_id_login")
    private String socialIdLogin;

    @Column(name = "free_trial_speaking_times")
    private Integer freeTrialSpeakingTimes = 0;

    @Column(name = "free_trial_writing_times")
    private Integer freeTrialWritingTimes = 0;

    @Column(name = "free_trial_listening_times")
    private Integer freeTrialListeningTimes = 0;

    @Column(name = "free_trial_reading_times")
    private Integer freeTrialReadingTimes = 0;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void removeRoles() {
        for (Role role : this.roles) {
            role.getUsers().remove(this);
        }
        this.roles.clear();
    }

    public void addRoles(List<Role> roles) {
        this.roles.clear();
        for (Role role : roles) {
            this.roles.add(role);
            role.getUsers().add(this);
        }
    }

    public void addDaysPremium(long plusDays) {
        this.premiumExpireTime = LocalDateTime.now().plusDays(plusDays);
    }

    public void addTrialReadingTimes() {
        this.freeTrialReadingTimes += 1;
    }
    public void addTrialListeningTimes() {
        this.freeTrialListeningTimes += 1;
    }
    public void addTrialWritingTimes() {
        this.freeTrialWritingTimes += 1;
    }
    public void addTrialSpeakingTimes() {
        this.freeTrialSpeakingTimes += 1;
    }
}
