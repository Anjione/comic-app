package com.example.comicbe.payload.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO implements Serializable {

    private String fullName;

    private String username;

    private String email;

    private String password;

    private String captchaToken;

    private String routerConfirm;
}
