package com.example.comicbe.payload.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.groups.Default;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateUserRequest implements Serializable {

    @NotBlank(groups = {Insert.class, Update.class}, message = "USER_FULL_NAME_NOT_EMPTY")
    private String fullName;

    @NotBlank(groups = {Insert.class}, message = "USER_USER_NAME_NOT_EMPTY")
    private String username;
    private String password;

    @NotBlank(groups = {Insert.class}, message = "USER_EMAIL_NOT_EMPTY")
    private String email;
    private String avatar;
    private String socialIdLogin;
    private boolean enabled;

    private boolean requiredChangePass = false;

    @NotEmpty(groups = {Insert.class, Update.class}, message = "USER_ROLE_NOT_EMPTY")
    private List<String> roleNames;

    public interface Insert extends Default {
    }

    public interface Update extends Default {
    }
}
